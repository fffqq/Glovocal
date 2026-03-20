CREATE TABLE IF NOT EXISTS restaurants(
    id SERIAL PRIMARY KEY ,
    name TEXT NOT NULL ,
    address TEXT NOT NULL,
    description TEXT);

/*
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    restaurant_id INT NOT NULL,
    user_id INT NOT NULL,--(потом отсюда получу аддрес надо будет его куда-то соханить,
    -- скорее всего переделать кастомер табличку)
    status VARCHAR(20),
    delivery_address VARCHAR(100),
        CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES customer(id)
);

 */




