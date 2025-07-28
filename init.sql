-- Initialize database with sample data
USE vehicle_db;

-- Create sample vehicles
INSERT INTO vehicles (type, model_code, brand_name, launch_date) VALUES
('SUV', 'BMW-X5-2023', 'BMW', '2023-01-15'),
('Sedan', 'AUDI-A4-2023', 'Audi', '2023-02-20'),
('Hatchback', 'VW-GOLF-2023', 'Volkswagen', '2023-03-10'),
('SUV', 'MERC-GLE-2023', 'Mercedes-Benz', '2023-04-05'),
('Coupe', 'BMW-M4-2023', 'BMW', '2023-05-12');