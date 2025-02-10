DROP VIEW IF EXISTS image_statistics_view;

CREATE VIEW image_statistics_view AS
SELECT
    ROW_NUMBER() OVER () AS id,
    COUNT(*) AS total_images,
    SUM(size) AS total_size,
    SUM(width * height) AS total_pixels,
    COUNT(CASE WHEN status = 'RECEIVED' THEN 1 END) AS received_images,
    COUNT(CASE WHEN status = 'PROCESSING' THEN 1 END) AS processing_images,
    COUNT(CASE WHEN status = 'PROCESSED' THEN 1 END) AS processed_images
FROM image;