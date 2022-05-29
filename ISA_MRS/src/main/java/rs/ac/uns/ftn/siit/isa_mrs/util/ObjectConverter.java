package rs.ac.uns.ftn.siit.isa_mrs.util;

import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.NewUserBasicInfoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDtos.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Address;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;

public interface ObjectConverter {
    User getUserFromSignUpRequestData(SignUpDto signUpRequestData, Address address);
    Request getRequestFromSignUpRequestData(SignUpDto signUpRequestData, User user);
    User getUserFromNewUserData(NewUserBasicInfoDto newUserInfo, Address address);
}
