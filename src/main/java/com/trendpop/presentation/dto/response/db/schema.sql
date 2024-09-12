CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(100) NOT NULL,
        password VARCHAR(100) NOT NULL,
        name VARCHAR(100) NOT NULL,
        nickname VARCHAR(100),
        description VARCHAR(255),
        profile_image_url VARCHAR(255),
        phone_number VARCHAR(20),
        oauth_provider_type VARCHAR(100),
        oauth_provider_id VARCHAR(255),
        deleted BOOLEAN DEFAULT FALSE,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);