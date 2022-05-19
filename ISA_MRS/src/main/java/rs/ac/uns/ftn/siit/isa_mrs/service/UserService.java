package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectOwnerDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.util.Collection;

public interface UserService {
    ResponseEntity<Collection<UserDto>> getUsers();
    ResponseEntity<UserDto> updateUserPassword(String email, String oldPassword, String newPassword);
    void encryptUsersPasswords();
    ResponseEntity<UserDto> changeUserStatus(Long id);
    ResponseEntity<String> activateUser(String token);
    ResponseEntity<String> resendVerificationMail(String email);
    ResponseEntity<PageDto<UserByTypeDto>> findUsersByTypeWithPaginationSortedByField(int offset, int pageSize,
                                                                                      String field, UserType type);
}
