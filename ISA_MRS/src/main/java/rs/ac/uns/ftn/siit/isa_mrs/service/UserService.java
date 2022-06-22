package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.NewUserBasicInfoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.UpdateUserDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectOwnerDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.util.Collection;

public interface UserService {
    ResponseEntity<Collection<UserDto>> getUsers();
    ResponseEntity<PageDto<UserDto>> getUsersPagination(int page, int pageSize, String token);
    ResponseEntity<UserDto> updateUserPassword(String email, String oldPassword, String newPassword);
    ResponseEntity<UserDto> createUser(NewUserBasicInfoDto newUserInfo);
    void encryptUsersPasswords();
    ResponseEntity<UserDto> changeUserStatus(Long id);
    ResponseEntity<Collection<UserDto>> changeUsersStatus(Collection<Long> ids);
    ResponseEntity<String> activateUser(String token);
    ResponseEntity<String> resendVerificationMail(String email);
    ResponseEntity<PageDto<UserByTypeDto>> findUsersByTypeWithPaginationSortedByField(int offset, int pageSize,
                                                                                      String field, UserType type);
}
