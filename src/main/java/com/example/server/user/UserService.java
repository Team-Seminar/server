package com.example.server.user;

import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.JWT.refreshToken.RefreshToken;
import com.example.server.global.security.JWT.refreshToken.RefreshTokenRepository;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(timeout = 60, readOnly = true, rollbackFor = CustomException.class)
public class UserService {
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokensDTO login(UserLoginDTO loginDTO) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        User user = userRepository.findByLoginIdAndPw(loginDTO.getLoginId(), loginDTO.getPw()).orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));
        return tokenManager.createToken(user.getId().toString(), user.getRole().toStr());
    }

    @Transactional
    public TokensDTO refresh(
            TokensDTO tokensDTO
    ){
        String refreshToken = tokensDTO.refreshToken();
        String sub = tokenManager.getSubject(refreshToken);
        RefreshToken hashRefreshToken = refreshTokenRepository.findById(sub)
                .orElseThrow(()->new CustomException(ErrorCode.TOKEN_NOT_FOUND));
        User user = userRepository.findById(UUID.fromString(sub)).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        //서버 refresh토큰과 입력된 refresh토큰의 동일 여부 검증
        if (!MessageDigest.isEqual(
                tokenManager.sha256Hashing(refreshToken).getBytes(),
                hashRefreshToken.getRefreshToken().getBytes()
        )){
            throw new CustomException(ErrorCode.TOKEN_FORGERY);
        }
        //access토큰과 refresh토큰의 subject 동일 여부 검증
        if (!user.getId().equals(UUID.fromString(tokenManager.getSubject(tokensDTO.accessToken())))){
            throw new CustomException(ErrorCode.TOKEN_FORGERY);
        }
        refreshTokenRepository.delete(hashRefreshToken);
        return tokenManager.createToken(user.getId().toString(), user.getRole().toStr());
    }
}