package com.example.translator.services.messaging;

import com.example.translator.dto.messaging.request.*;
import com.example.translator.dto.messaging.response.*;

public interface Messaging {

    SendTranslationResponseDto sendTranslationByEmail(SendTranslationRequestDto sendTranslationRequestDto);

    SendVerificationResponseDto sendVerificationEmail(SendVerificationRequestDto sendVerificationRequestDto);

    SendActivationResponseDto sendAccountRecoveryMessage(SendActivationRequestDto sendActivationRequestDto);

    SendWelcomeMessageResponseDto sendWelcomeEmail(SendWelcomeMessageRequestDto sendWelcomeMessageRequestDto);

    SendBlockMessageResponseDto sendBlockEmail(SendBlockMessageRequestDto sendBlockMessageRequestDto);



}
