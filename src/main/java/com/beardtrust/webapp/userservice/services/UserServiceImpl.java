package com.beardtrust.webapp.userservice.services;

import java.util.*;

import com.beardtrust.webapp.userservice.dtos.UserDTO;
import com.beardtrust.webapp.userservice.exceptions.DuplicateEntryException;
import com.beardtrust.webapp.userservice.models.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beardtrust.webapp.userservice.entities.UserEntity;
import com.beardtrust.webapp.userservice.repos.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(UserRegistration userRegistration) {
        log.trace("Registering user...");
        UserEntity userEntity = searchRepositoryForDuplicates(userRegistration);

        UserDTO userDTO = null;

        if (userEntity == null) {
            log.info("Attempting to save user " + userRegistration.getUsername() + " to database");
            try {
                userDTO = createUser(userRegistration);
                log.info("UserEntity " + userRegistration.getUsername() + " saved to database");
            } catch (Exception e) {
                log.error("Failed to save user " + userRegistration.getUsername() + " to database");
            }
        } else {
            throwDuplicateEntryException(userRegistration, userEntity);
        }
        log.trace("Returning UserDTO...");
        log.debug("UserDTO returned: " + userDTO);
        return userDTO != null ? userDTO.getId() : null;
    }

    /**
     * This method creates a UserEntity object from the provided
     * UserRegistration, saves that UserEntity to the database and then returns
     * a UserDTO created from the saved UserEntity.
     *
     * @param userRegistration UserRegistration the registration data sent to
     * the server
     * @return UserDTO a new UserDTO for the UserEntity saved in the database
     */
    private UserDTO createUser(UserRegistration userRegistration) {
        log.trace("Creating user...");
        log.debug("Registration email found: " + userRegistration.getEmail());
        log.debug("Registration phone found: " + userRegistration.getPhone());
        userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userRegistration, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setRole("user");
        log.trace("Saving user entity...");
        log.debug("User Entity being saved: " + userEntity);
        return modelMapper.map(userRepository.save(userEntity), UserDTO.class);
    }

    /**
     * This method searches for a user in the database, locating any user with
     * associated fields that are required to be unique, including email
     * address, username, and phone number.
     *
     * @param userRegistration UserRegistration the user registration details
     * @return UserEntity a UserEntity object representing the user as found in
     * the database
     */
    private UserEntity searchRepositoryForDuplicates(UserRegistration userRegistration) {
        log.trace("Checking for duplicate registrations...");
        log.debug("Registration email found: " + userRegistration.getEmail());
        log.debug("Registration phone found: " + userRegistration.getPhone());
        UserEntity userEntity;
        userEntity = userRepository.findByEmail(userRegistration.getEmail());

        if (userEntity == null) {
            log.debug("User entity not found by email, searching by username");
            userEntity = userRepository.findByUsername(userRegistration.getUsername());
        }

        if (userEntity == null) {
            log.debug("User entity not found by email or username, searching by phone");
            userEntity = userRepository.findByPhone(userRegistration.getPhone());
        }
        log.trace("Returning user entity: " + userEntity);
        return userEntity;
    }

    /**
     * This method throws an appropriate duplicate entry exception.
     *
     * @param userRegistration UserRegistration the user registration object
     * @param userEntity UserEntity the user entity found in the database
     */
    private void throwDuplicateEntryException(UserRegistration userRegistration, UserEntity userEntity) {
        log.trace("Duplicate user registratiion found..");
        log.error("User " + userRegistration.getUsername() + " cannot be saved due to duplicate "
                + "values in database");

        String registrationEmail = userRegistration.getEmail();
        String entityEmail = userEntity.getEmail();

        String registrationUsername = userRegistration.getUsername();
        String entityUsername = userEntity.getUsername();

        String registrationPhone = userRegistration.getPhone();
        String entityPhone = userEntity.getPhone();

        if (registrationEmail.equals(entityEmail) && registrationUsername.equals(entityUsername)
                && registrationPhone.equals(entityPhone)) {
            log.error(String.format("User with email of '%s', username of '%s', and phone number of '%s' already"
                    + " exists in database", registrationEmail, registrationUsername, registrationPhone));
            throw new DuplicateEntryException("User with this email, username, and phone number "
                    + "already exists");
        }

        if (registrationEmail.equals(entityEmail)) {
            log.error(String.format("Email address '%s' already present in database", entityEmail));
            throw new DuplicateEntryException(String.format("email address '%s' already registered",
                    entityEmail));
        }

        if (registrationUsername.equals(entityUsername)) {
            log.error(String.format("Username '%s' already present in database", entityUsername));
            throw new DuplicateEntryException(String.format("username '%s' already registered",
                    entityUsername));
        }

        if (registrationPhone.equals(entityPhone)) {
            log.error(String.format("Phone number '%s' already present in database", entityPhone));
            throw new DuplicateEntryException(String.format("phone number '%s' already registered",
                    entityPhone));
        }
    }

    @Override
    public UserDTO displayUser(String userId) {
        log.trace("Displaying user with Id: " + userId);
        log.info("Looking up details for user " + userId);
        Optional<UserEntity> user = userRepository.findById(userId);
        UserDTO userDetails = null;
        if (user.isPresent()) {
            log.info("UserEntity " + user.get().getUserId() + " located in database");
            ModelMapper modelMapper = new ModelMapper();
            userDetails = modelMapper.map(user.get(), UserDTO.class);
        }
        log.trace("Returning user details...");
        return userDetails;
    }

    @Override
    public UserDTO getUserDetailsByEmail(String username) {
        log.trace("Getting user by email...");
        log.debug("Username searched by: " + username);
        UserEntity user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        log.trace("Returning user DTO...");
        return new ModelMapper().map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.trace("Loading user by username...");
        log.debug("Username to load by: " + s);
        UserEntity user = userRepository.findByEmail(s);

        if (user == null) {
            log.trace("User not found by username...");
            throw new UsernameNotFoundException(s);
        }
        log.trace("Returning user details...");
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), true, true,
                true, true, new ArrayList<>());
    }

    @Override
    @Transactional
    public List<UserEntity> getAll() {
        log.trace("Getting all user entities...");

        List<UserEntity> list = userRepository.findAll();
        log.trace("Returning user entity list...");
        log.debug("User list size found: " + list.size());
        return list;
    }

    @Override
    @Transactional
    public UserEntity getById(String id) {
        log.trace("Getting user by Id...");
        log.debug("User Id searched by: " + id);

        Optional<UserEntity> result = userRepository.findById(id);

        UserEntity user = null;

        if (result.isPresent()) {
            log.trace("User found...");
            user = result.get();
        } else {
            log.error("User not found!!!");
            throw new RuntimeException("User id - " + id + " not found");
        }
        log.trace("Returning user entity...");
        return user;
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.trace("Deleting by Id...");
        log.debug("Id used to delete: " + id);

        Optional<UserEntity> result = userRepository.findById(id);

        if (result.isPresent()) {
            log.trace("User found by Id. Able to delete...");
            userRepository.deleteById(id);
        } else {
            log.error("User not found by Id. Unable to delete!!!");
            throw new RuntimeException("User id - " + id + " not found");
        }
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        log.trace("Saving user entity...");
        log.debug("User email being saved: " + user.getEmail());
        log.debug("User phone being saved: " + user.getPhone());
        if (user.getPassword() == null) {
            log.trace("Password not found, searching...");
            Optional<UserEntity> ouser = userRepository.findById(user.getUserId());
            user.setPassword(ouser.get().getPassword());
        } else {
            log.trace("Password being saved...");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        log.trace("Saving user...");
        userRepository.save(user);
    }

    public String updateService(UserEntity u, String id) {
        log.trace("Attempting to update user...");
        log.debug("User Id searched by: " + id);
        Optional<UserEntity> ouser = userRepository.findById(id);
        if (ouser.isPresent()) {
            log.trace("User successfully found...");
            u.setUserId(ouser.get().getUserId());
            u.setRole(ouser.get().getRole());
            log.trace("Saving user update...");
            userRepository.save(u);
            return "Update complete!";
        } else {
            log.error("User not found!!!");
            return "Entity not found!";
        }

    }
    //Admin Access required

    public List<UserEntity> getAllUserInfos() {
        log.trace("Getting all users...");
        List<UserEntity> list = userRepository.findAll();
        log.debug("List size found: " + list.size());
        log.trace("Returning user list...");
        return list;
    }

    public Optional<UserEntity> getSpecificUserInfos(String account_id) {
        log.trace("Searching for a specific user...");
        log.debug("Id searched by: " + account_id);
        log.trace("Returning optional user...");
        return userRepository.findById(account_id);
    }

    public Page<UserEntity> findPaginated(int pageNumber, int pageSize, String[] sortBy, String search) {
        List<Sort.Order> orders = parseOrders(sortBy);
        System.out.println("orders parsed: " + orders);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));
        log.trace("Searching for users entities with pagination...");
        log.debug("Page data provided: " + pageable);
        log.debug("Search provided: " + search);
        Page<UserEntity> page;
        if (search == null) {
            log.trace("No search provided, generic findAll...");
            page = userRepository.findAll(pageable);
        } else {
            log.trace("Search providedm building page object...");
            page = userRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(search, search, search, search, search, pageable);
        }
        for (UserEntity user : page) {
            log.trace("Stripping user of password for display purposes...");
            user.setPassword(null);
        }
        log.trace("Returning page...");
        log.debug("Page created: " + page);
        return page;
    }

    /**
     * This method accepts a string and returns the intended sort direction, ascending or descending.
     *
     * @param direction String the string indicating the desired sort direction
     * @return Sort.Direction the direction in which to sort
     */
    private Sort.Direction getSortDirection(String direction) {
        Sort.Direction returnValue = Sort.Direction.ASC;

        if (direction.equals("desc")) {
            returnValue = Sort.Direction.DESC;
        }

        return returnValue;
    }

    /**
     * This method takes an array of strings, sortBy, and parses that string to produce
     * a list of sort orders to use.
     *
     * @param sortBy String[] the string of sorting instructions
     * @return List<Sort.Order> the parsed collection of sorting instructions
     */
    private List<Sort.Order> parseOrders(String[] sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.length > 2) {
            for (int i = 0; i < sortBy.length; i++) {
                String sort = sortBy[i];
                String dir = sortBy[i + 1];
                orders.add(new Sort.Order(getSortDirection(dir), sort));
                i++;
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sortBy[1]), sortBy[0]));
        }

        return orders;
    }
}
