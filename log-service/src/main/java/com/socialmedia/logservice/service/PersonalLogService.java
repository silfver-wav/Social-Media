package com.socialmedia.logservice.service;

import com.socialmedia.logservice.ValueObject.User;
import com.socialmedia.logservice.interfaces.IPersonalLogService;
import com.socialmedia.logservice.model.Chart;
import com.socialmedia.logservice.model.PersonalLogDTO;
import com.socialmedia.logservice.model.PersonalLogRequest;
import com.socialmedia.logservice.repository.ChartRepository;
import lombok.AllArgsConstructor;
import com.socialmedia.logservice.model.PersonalLog;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.socialmedia.logservice.repository.PersonalLogRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for personal log.
 */
@Service
@AllArgsConstructor
public class PersonalLogService implements IPersonalLogService {
    private final PersonalLogRepository personalLogRepository;

    private final ChartRepository chartRepository;
    private RestTemplate restTemplate;


    @Override
    public ResponseEntity<Object> add(PersonalLogRequest personalLogRequest) {
        if (personalLogRequest.getText() == null && personalLogRequest.getImage() == null && personalLogRequest.getChart() == null) {
            return new ResponseEntity<>("No content", HttpStatus.BAD_REQUEST);
        }
        if (personalLogRequest.getChart() != null) {
            Chart chart = personalLogRequest.getChart();
            chartRepository.save(chart);
        }

        try {
            PersonalLog personalLog = new PersonalLog();
            personalLog.setText(personalLogRequest.getText());
            personalLog.setUsername(personalLogRequest.getUsername());
            personalLog.setChart(personalLogRequest.getChart());
            personalLog.setCreatedAt(LocalDateTime.now());


            String base64EncodedImage = personalLogRequest.getImage();
            if (base64EncodedImage != null) {
                byte[] image = Base64.getDecoder().decode(base64EncodedImage);
                personalLog.setImage(image);
            }
            personalLog = personalLogRepository.save(personalLog);
            //System.out.println("Personal log created: " + personalLog.toString());

            return ResponseHandler.generateResponse(HttpStatus.CREATED, "Personal log created");
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "Personal log creation failed");
        }
    }

    /**
     * Gets all personal logs by the user from the database.
     * @param username
     * @return HTTP status OK if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> getAllByUser(String username) {
        List<PersonalLog> personalLogs = personalLogRepository.findByUsername(username);
        if (personalLogs.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "No personal logs found");
        }

        List<PersonalLogDTO> personalLogDTOS = new ArrayList<>();
        for (PersonalLog personalLog : personalLogs) {
            PersonalLogDTO personalLogDTO = new PersonalLogDTO();
            personalLogDTO.setText(personalLog.getText());
            personalLogDTO.setUsername(personalLog.getUsername());
            personalLogDTO.setCreatedAt(personalLog.getCreatedAt());
            personalLogDTO.setChart(personalLog.getChart());
            if (personalLog.getImage() != null) {
                String base64EncodedImage = Base64.getEncoder().encodeToString(personalLog.getImage());
                personalLogDTO.setImage(base64EncodedImage);
            }

            personalLogDTOS.add(personalLogDTO);
        }

        return ResponseHandler.generateResponse(HttpStatus.OK, "Personal logs found", personalLogDTOS);
    }

    /**
     * Gets all personal logs the users user follows from the database.
     * @param username
     * @return HTTP status OK if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> getFollowingPosts(String username) {
        if (username.isEmpty())
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "No username provided",new Object());
        }

        HashMap<String, Long> params = new HashMap<>();
        //ResponseEntity<List<User>> rs = restTemplate.getForEntity("http://localhost:8083/user/getFollowing2/"+username, User.class, params);
        ResponseEntity<List<User>> rs = restTemplate.exchange(
                "http://localhost:8083/user/getFollowing2/{username}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>(){},
                username
        );


        if (!rs.hasBody()) return ResponseHandler.generateResponse(HttpStatus.OK, "User is not following anyone",new Object());

        List<User> following = rs.getBody();
        List<PersonalLog> personalLogs = new ArrayList<>();
        for (User user : following) {
            List<PersonalLog> list = personalLogRepository.findAllByUsername(user.getUsername());
            personalLogs.addAll(list);
        }

        Collections.sort(personalLogs);

        return ResponseHandler.generateResponse(HttpStatus.OK, "logs found",personalLogs);
    }

}
