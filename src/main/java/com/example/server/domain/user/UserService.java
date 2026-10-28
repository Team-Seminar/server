package com.example.server.domain.user;

import com.example.server.global.common.dto.TokensDTO;
import com.example.server.domain.user.presentation.dto.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.JWT.refreshToken.RefreshToken;
import com.example.server.global.security.JWT.refreshToken.RefreshTokenRepository;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import com.example.server.domain.user.presentation.dto.UserDTO;
import com.example.server.domain.user.presentation.dto.UserJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
@Transactional(timeout = 60, readOnly = true, rollbackFor = CustomException.class)
public class UserService {
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    //개인 상세 조회
    public UserDTO getMe(String token){
        User user = userRepository.findById(tokenManager.getSubject(token))
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserDTO(user.getLoginId(), user.getRole());
    }

    //회원가입
    @Transactional
    public void join(UserJoinDTO dto, UserRole role) {
        //확인용 비밀번호와 입력값 동일 여부
        if (!dto.pw().equals(dto.checkPw())) {
            throw new CustomException(ErrorCode.NOT_EQUALS_PASSWORD);
        }

        //DB에 로그인 아이디 중복 불가 제약에 의한 예외처리
        if (userRepository.existsByLoginId(dto.loginId())) {
            throw new CustomException(ErrorCode.IS_USE_NAME);
        }

        User user = User.builder()
                .role(role)
                .loginId(dto.loginId())
                .pw(passwordEncoder.encode(dto.pw()))
                .build();
        userRepository.save(user);
    }

    //로그인
    @Transactional
    public TokensDTO login(UserLoginDTO loginDTO) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        User user = userRepository.findByLoginId(loginDTO.loginId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));
        //비밀번호 검증
        if(!passwordEncoder.matches(loginDTO.pw(), user.getPw())){
            throw new CustomException(ErrorCode.NOT_ALLOW_LOGIN);
        }
        return tokenManager.createToken(user.getId(), user.getRole().toStr());
    }

    //토큰 리프레쉬
    @Transactional
    public TokensDTO refresh(
            TokensDTO tokensDTO
    ){
        String refreshToken = tokensDTO.refreshToken();
        String sub = tokenManager.getSubject(refreshToken);
        RefreshToken hashRefreshToken = refreshTokenRepository.findById(sub)
                .orElseThrow(()->new CustomException(ErrorCode.TOKEN_NOT_FOUND));
        User user = userRepository.findById(sub).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        //서버 refresh토큰과 입력된 refresh토큰의 동일 여부 검증
        if (!MessageDigest.isEqual(
                tokenManager.sha256Hashing(refreshToken).getBytes(),
                hashRefreshToken.getRefreshToken().getBytes()
        )){
            throw new CustomException(ErrorCode.TOKEN_FORGERY);
        }
        //access토큰과 refresh토큰의 subject 동일 여부 검증
        if (!user.getId().equals(tokenManager.getSubject(tokensDTO.accessToken()))){
            throw new CustomException(ErrorCode.TOKEN_FORGERY);
        }
        refreshTokenRepository.delete(hashRefreshToken);
        return tokenManager.createToken(user.getId(), user.getRole().toStr());
    }
}