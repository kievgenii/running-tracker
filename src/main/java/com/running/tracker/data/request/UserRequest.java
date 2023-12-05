package com.running.tracker.data.request;

import com.running.tracker.annotation.NameEnglish;
import com.running.tracker.data.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NameEnglish
    @NotNull
    private String firstName;
    @NameEnglish
    @NotNull
    private String lastName;
    @Past
    private LocalDate birthDate;
    private Gender sex;
}
