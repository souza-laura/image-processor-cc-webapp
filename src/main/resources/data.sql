DROP VIEW IF EXISTS image_statistics_view;

CREATE VIEW image_statistics_view AS
SELECT
    ROW_NUMBER() OVER () AS id,
    COUNT(*) AS total_images,
    SUM(byte_size) AS total_size,
    SUM(width * height) AS total_pixels,
    COUNT(CASE WHEN elaboration_state = 'RECEIVED' THEN 1 END) AS received_images,
    COUNT(CASE WHEN elaboration_state = 'PROCESSING' THEN 1 END) AS processing_images,
    COUNT(CASE WHEN elaboration_state = 'PROCESSED' THEN 1 END) AS processed_images
FROM images;