package pl.beng.thesis.controller;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.beng.thesis.model.DTO.EditEmployeeDTO;
import pl.beng.thesis.model.DTO.NewPasswordDTO;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.service.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Method used to fetch employee from JWT.
     * @return Employee with given token
     */
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<Employee> findLoggedEmployee() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee loggedEmployee = employeeService.findByUsername(username);

        return new ResponseEntity<>(loggedEmployee, HttpStatus.OK);
    }

    /**
     * Find and return list of employees
     *
     * @return all employees list
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> findAll() {

        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    /**
     * Find and return employee with specified id
     *
     * @param id employee id
     * @return Employee with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> findOne(@PathVariable("id") long id) {

        return new ResponseEntity<>(employeeService.find(id), HttpStatus.OK);
    }

    /**
     * Find and return employee with specified username
     *
     * @param username given username
     * @return Employee with given username
     */
    @RequestMapping(value = "/employee/{username}", method = RequestMethod.GET)
    public ResponseEntity<Employee> findOne(@PathVariable("username") String username) {

        return new ResponseEntity<>(employeeService.findByUsername(username), HttpStatus.OK);
    }

        /**
         * Find and update employee with given body
         *
         * @param updatedEmployee employee with new values
         * @return Employee with updated fields
         */
    @RequestMapping(value = "/employee/{username}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@RequestHeader(value = "ETag") int ETag,
                                                   @PathVariable String username,
                                                   @RequestBody EditEmployeeDTO updatedEmployee) {

        return new ResponseEntity<>(employeeService.updateEmployee(updatedEmployee, username, ETag), HttpStatus.OK);
    }

    /**
     * Create new employee.
     * @param newEmployee employee to persist.
     * @return created employee.
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {

        return new ResponseEntity<>(employeeService.createEmployee(newEmployee), HttpStatus.CREATED);
    }



    @RequestMapping(value = "/employee/{username}/password", method = RequestMethod.PUT)
    public ResponseEntity<Employee> changePassword(@RequestHeader(value = "ETag") int ETag,
                                                   @PathVariable("username") String username,
                                                   @RequestBody NewPasswordDTO newPasswordDTO) throws Exception {

        return new ResponseEntity<>(employeeService.changePassword
                        (username,
                         newPasswordDTO.getNewPassword(),
                         newPasswordDTO.getOldPassword(),
                         newPasswordDTO.getNewPasswordConfirmed(),
                         ETag),
            HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{username}/activated", method = RequestMethod.PUT)
    public ResponseEntity<Employee> activeAccount(@RequestHeader(value = "ETag") int ETag,
                                                  @PathVariable("username") String username,
                                                  @RequestParam boolean active) {

        return new ResponseEntity<>(employeeService.activate(username, active, ETag), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{username}/locked", method = RequestMethod.PUT)
    public ResponseEntity<Employee> lockAccount(@RequestHeader(value = "ETag") int ETag,
                                                @PathVariable("username") String username,
                                                @RequestParam boolean locked) {

        return new ResponseEntity<>(employeeService.lock(username, locked, ETag), HttpStatus.OK);
    }


    /**
     * Handle employee photo upload
     **/
    @RequestMapping(value = "/employee/{username}/photography", method = RequestMethod.POST)
    public ResponseEntity uploadEmployeePhotography(@PathVariable("username") String username,
                                                    @RequestParam("photography") MultipartFile photography) throws IOException, FileUploadException {

        if (photography.isEmpty()) {
            throw new FileUploadException("File.Empty");
        }
        this.employeeService.uploadEmployeesPhotography(username, photography);
        return ResponseEntity.ok("Successful upload");
    }


    /**
     * Get employees profile picture
     * @param username given employee username
     * @return employee profile picture
     * @throws IOException in case when IO operation fails
     */
    @RequestMapping(value = "/employee/{username}/photography", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getEmployeeProfilePicture(@PathVariable("username") String username) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(this.employeeService.getEmployeeProfilePicture(username), headers, HttpStatus.OK);
    }
}
