package com.socialmedia.logservice.ValueObject;

import com.socialmedia.logservice.model.PersonalLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {

    private PersonalLog personalLog;
    private Following following;
}
