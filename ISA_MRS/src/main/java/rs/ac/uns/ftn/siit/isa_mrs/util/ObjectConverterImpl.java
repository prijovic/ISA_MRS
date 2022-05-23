package rs.ac.uns.ftn.siit.isa_mrs.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.NewUserBasicInfoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ObjectConverterImpl implements ObjectConverter {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserFromSignUpRequestData(SignUpDto signUpRequestData, Address address) {
        SaleParticipant user;
        if (signUpRequestData.getUserType().equals(UserType.Client)) {
            user = new Client();
        }
        else {
            user = new RentalObjectOwner();
        }
        user.setUserType(signUpRequestData.getUserType());
        user.setName(signUpRequestData.getName());
        user.setSurname(signUpRequestData.getSurname());
        user.setPassword(passwordEncoder.encode(signUpRequestData.getPassword()));
        user.setEmail(signUpRequestData.getEmail());
        user.setPhone(signUpRequestData.getPhoneNumber());
        user.setAddress(address);
        user.setIsActive(false);
        user.setPoints(0);
        return user;
    }

    @Override
    public Request getRequestFromSignUpRequestData(SignUpDto signUpRequestData, User user) {
        Request request = new Request();
        request.setStatus(RequestStatus.Pending);
        request.setReason(signUpRequestData.getReason());
        request.setType(RequestType.SignUp);
        request.setTimeStamp(LocalDateTime.now());
        request.setUser(user);
        return request;
    }

    @Override
    public User getUserFromNewUserData(NewUserBasicInfoDto newUserInfo, Address address) {
        User user;
        if (newUserInfo.getUserType().equals(UserType.Admin)) {
            user = new Admin();
        } else {
            if (newUserInfo.getUserType().equals(UserType.Client)) {
                user = new Client();
            }
            else {
                user = new RentalObjectOwner();
            }
            ((SaleParticipant) user).setPoints(0);
        }
        user.setUserType(newUserInfo.getUserType());
        user.setName(newUserInfo.getName());
        user.setSurname(newUserInfo.getSurname());
        user.setEmail(newUserInfo.getEmail());
        user.setPhone(newUserInfo.getPhoneNumber());
        user.setAddress(address);
        user.setIsActive(true);
        return user;
    }
}
