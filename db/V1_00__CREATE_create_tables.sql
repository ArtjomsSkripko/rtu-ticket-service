CREATE TABLE "transport_system"."ticket"(
    "company_name" varchar(100) ,
    "place_type" varchar(100) ,
    "transport_type" varchar(100) ,
    "dep_city" varchar(100) ,
    "dep_street" varchar(100) ,
    "dep_home" varchar(100) ,
    "dest_home" varchar(100) ,
    "dest_street" varchar(100) ,
    "dest_city" varchar(100) ,
    "dep_time" varchar(100) ,
    "ticket_id" varchar(100) NOT NULL,
    "type" varchar(100) ,
    "passenger" varchar(100) ,
    "price_with_discount" varchar(100) ,
    "price_without_discount" varchar(100) ,
    "tax_rate" varchar(100) ,
    "route_number" varchar(100) ,
    "valid_until" varchar(100) ,
    "user_id" varchar(100) ,
    "discount" varchar(100) ,
    "offer_id" varchar(100) NOT NULL
);
