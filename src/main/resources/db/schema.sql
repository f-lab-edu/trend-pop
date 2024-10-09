CREATE TABLE users (
        id VARCHAR(100) PRIMARY KEY,
        email VARCHAR(100) NOT NULL,
        password VARCHAR(100) NOT NULL,
        name VARCHAR(100) NOT NULL,
        nickname VARCHAR(100),
        description VARCHAR(255),
        profile_image_url VARCHAR(255),
        phone_number VARCHAR(20),
        oauth_provider_type VARCHAR(100),
        oauth_provider_id VARCHAR(255),
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE locations (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES locations(id)
);

CREATE TABLE stores (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(255),
    location_id VARCHAR(255),
    start_at DATE,
    end_at DATE,
    open_time TIME,
    close_time TIME,
    price INT,
    reserve_closed BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    location_detail VARCHAR(255),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);

CREATE TABLE recommended_stores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id VARCHAR(255) NOT NULL,
    priority INT DEFAULT 1,
    image_url VARCHAR(255),
    recommended_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES stores(id)
);

CREATE TABLE reservation_status_codes (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE reservations (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    store_id VARCHAR(255),
    visit_at DATE,
    visit_time TIME,
    guest_count INT,
    status VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (store_id) REFERENCES stores(id),
    FOREIGN KEY (status) REFERENCES reservation_status_codes(code);
);

CREATE TABLE store_view_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    store_id VARCHAR(255),
    viewed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (store_id) REFERENCES stores(id)
);

CREATE TABLE store_photos (
    id VARCHAR(255) PRIMARY KEY,
    store_id VARCHAR(255),
    image_url VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES stores(id)
);