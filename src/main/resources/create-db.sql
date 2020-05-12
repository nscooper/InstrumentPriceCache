	CREATE MEMORY TABLE price (
  id         INTEGER IDENTITY PRIMARY KEY,
  instrument_id INTEGER,
  vendor_id INTEGER,
  bid_price  DOUBLE,
  offer_price  DOUBLE,
  created TIMESTAMP default CURRENT_DATE
);

CREATE MEMORY TABLE instrument (
  id         INTEGER IDENTITY PRIMARY KEY,
  name  VARCHAR(200),
  isin  VARCHAR(50)
);

CREATE MEMORY TABLE vendor (
  id         INTEGER IDENTITY PRIMARY KEY,
  name  VARCHAR(200)
);

ALTER TABLE price 
   add constraint fk_price_2_instrument
   foreign key (instrument_id) references instrument(id)
   on delete cascade;
   
ALTER TABLE price 
   add constraint fk_price_2_vendor
   foreign key (vendor_id) references vendor(id)
   on delete cascade;
  