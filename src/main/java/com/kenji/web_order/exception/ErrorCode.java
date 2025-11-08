package com.kenji.web_order.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You have no permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Invalid age, you must be at least {min} y.o", HttpStatus.BAD_REQUEST),
    INVALID_MAIL(1009, "Invalid mail, example: example@gmail.com", HttpStatus.BAD_REQUEST),
    BLANK_DATA(1009, "Blank data", HttpStatus.BAD_REQUEST),

    ORDER_EXISTED(2001, "Order existed", HttpStatus.BAD_REQUEST),
    ORDER_NOT_EXISTED(2002, "Order not existed", HttpStatus.NOT_FOUND),

    ROLE_EXISTED(3001, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(3002, "Role not existed", HttpStatus.NOT_FOUND),
    ROLES_EMPTY(3003, "Role request is empty", HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_EXISTED(4001, "Product not existed", HttpStatus.NOT_FOUND),

    CATEGORY_NOT_EXISTED(5001, "Category not existed", HttpStatus.NOT_FOUND),

    TOKEN_EXPIRED(6001, "Token is expired", HttpStatus.BAD_REQUEST),
    NO_USER_TOKEN(6002, "No user match to token", HttpStatus.BAD_REQUEST),

    PROMOTION_NOT_EXISTED(7002, "No user match to token", HttpStatus.NOT_FOUND)
    ;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.statusCode = status;
    }

    public String getMessage() {
        return message;
    }

    int code;
    String message;
    HttpStatusCode statusCode;
}
