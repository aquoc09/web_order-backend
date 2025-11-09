package com.kenji.web_order.service.mail;

import com.kenji.web_order.entity.User;

public interface MailService {
    String forgotPassword(User user);
}
