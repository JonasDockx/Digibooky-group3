package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.CreateLibrarianDTO;
import com.switchfully.digibookygroup3.api.dtos.LibrarianDTO;
import com.switchfully.digibookygroup3.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class LibrarianMapper {
    public User mapDTOToLibrarian(CreateLibrarianDTO createLibrarianDTO) {
        return User.createLibrarian(
                createLibrarianDTO.getLastname(),
                createLibrarianDTO.getFirstname(),
                createLibrarianDTO.getEmail(),
                createLibrarianDTO.getPassword());
    }

    public LibrarianDTO mapLibrarianToLibrarianDTO(User user) {
        return new LibrarianDTO(
                user.getId(),
                user.getLastname(),
                user.getFirstname(),
                user.getEmail(),
                user.getUserType());
    }
}
