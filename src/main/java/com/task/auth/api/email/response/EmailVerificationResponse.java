package com.task.auth.api.email.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationResponse {

    private boolean validEmail;
}
