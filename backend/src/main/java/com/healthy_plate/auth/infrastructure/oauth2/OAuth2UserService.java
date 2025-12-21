package com.healthy_plate.auth.infrastructure.oauth2;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import com.healthy_plate.user.domain.model.Email;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.model.UserProfile;
import com.healthy_plate.user.domain.model.UserRole;
import com.healthy_plate.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Provider provider = OAuth2Provider.valueOf(registrationId.toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        User user = userRepository.findByProviderAndProviderId(provider, userInfo.getProviderId())
            .orElseGet(() -> createUser(provider, userInfo));

        return new CustomOAuth2User(
            user.getId(),
            user.getEmail().getValue(),
            user.getRole(),
            oAuth2User.getAttributes()
        );
    }

    private User createUser(OAuth2Provider provider, OAuth2UserInfo userInfo) {
        Email email = Email.of(userInfo.getEmail());
        UserProfile profile = UserProfile.createEmpty();
        User user = new User(
            email,
            profile,
            provider,
            userInfo.getProviderId(),
            UserRole.ROLE_USER
        );
        return userRepository.save(user);
    }


}
