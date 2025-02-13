package it.ldas.imageprocessor.service;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.repository.ImageRepository;
import it.ldas.imageprocessor.utils.enums.ElaborationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ImageProcessorAsyncService.class})
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ImageProcessorAsyncServiceTest {

    @MockitoBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageProcessorAsyncService imageProcessorAsyncService;

    private Image image;

    @BeforeEach
    void setUp() {
        image = new Image();
        image.setId(1L);
        image.setOriginalName("dummyphoto.png");
        image.setElaborationState(ElaborationState.PROCESSING);
    }

    @Test
    void testProcessImage_OK() {
        Long imageId = 1L;
        String dir = "/path/to/image/dummyphoto.png";

        ImageProcessorAsyncService spyService = Mockito.spy(imageProcessorAsyncService);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        doReturn("Image:\n" +
                "  Filename: /Users/lauradealmeidasouza/Desktop/aton_code_challenge/image-processor-webapp/uploaded_images/35_1i.jpg\n" +
                "  Permissions: rw-r--r--\n" +
                "  Format: JPEG (Joint Photographic Experts Group JFIF format)\n" +
                "  Mime type: image/jpeg\n" +
                "  Class: DirectClass\n" +
                "  Geometry: 1514x1340+0+0\n" +
                "  Resolution: 72x72\n" +
                "  Print size: 21.0278x18.6111\n" +
                "  Units: PixelsPerInch\n" +
                "  Colorspace: sRGB\n" +
                "  Type: TrueColor\n" +
                "  Base type: Undefined\n" +
                "  Endianness: Undefined\n" +
                "  Depth: 8-bit\n" +
                "  Channels: 3.0\n" +
                "  Channel depth:\n" +
                "    Red: 8-bit\n" +
                "    Green: 8-bit\n" +
                "    Blue: 8-bit\n" +
                "  Channel statistics:\n" +
                "    Pixels: 2028760\n" +
                "    Red:\n" +
                "      min: 0  (0)\n" +
                "      max: 255 (1)\n" +
                "      mean: 103.058 (0.404149)\n" +
                "      median: 151 (0.592157)\n" +
                "      standard deviation: 80.0228 (0.313815)\n" +
                "      kurtosis: -1.80705\n" +
                "      skewness: -0.179599\n" +
                "      entropy: 0.858571\n" +
                "    Green:\n" +
                "      min: 0  (0)\n" +
                "      max: 255 (1)\n" +
                "      mean: 112.239 (0.440154)\n" +
                "      median: 152 (0.596078)\n" +
                "      standard deviation: 95.7707 (0.375571)\n" +
                "      kurtosis: -1.87239\n" +
                "      skewness: -0.140676\n" +
                "      entropy: 0.78708\n" +
                "    Blue:\n" +
                "      min: 0  (0)\n" +
                "      max: 255 (1)\n" +
                "      mean: 124.023 (0.486366)\n" +
                "      median: 190 (0.745098)\n" +
                "      standard deviation: 100.759 (0.395134)\n" +
                "      kurtosis: -1.84937\n" +
                "      skewness: -0.211058\n" +
                "      entropy: 0.810606\n" +
                "  Image statistics:\n" +
                "    Overall:\n" +
                "      min: 0  (0)\n" +
                "      max: 255 (1)\n" +
                "      mean: 113.107 (0.443557)\n" +
                "      median: 164.333 (0.644444)\n" +
                "      standard deviation: 92.1842 (0.361507)\n" +
                "      kurtosis: -1.84294\n" +
                "      skewness: -0.177111\n" +
                "      entropy: 0.818752\n" +
                "  Rendering intent: Perceptual\n" +
                "  Gamma: 0.454545\n" +
                "  Chromaticity:\n" +
                "    red primary: (0.64,0.33,0.03)\n" +
                "    green primary: (0.3,0.6,0.1)\n" +
                "    blue primary: (0.15,0.06,0.79)\n" +
                "    white point: (0.3127,0.329,0.3583)\n" +
                "  Matte color: grey74\n" +
                "  Background color: white\n" +
                "  Border color: srgb(223,223,223)\n" +
                "  Transparent color: black\n" +
                "  Interlace: None\n" +
                "  Intensity: Undefined\n" +
                "  Compose: Over\n" +
                "  Page geometry: 1514x1340+0+0\n" +
                "  Dispose: Undefined\n" +
                "  Iterations: 0\n" +
                "  Compression: JPEG\n" +
                "  Quality: 95\n" +
                "  Orientation: Undefined\n" +
                "  Profiles:\n" +
                "    Profile-exif: 634 bytes\n" +
                "    Profile-icc: 536 bytes\n" +
                "  Properties:\n" +
                "    comment: Voxxed Milano 2022\n" +
                "    date:create: 2025-02-11T22:02:30+00:00\n" +
                "    date:modify: 2025-02-11T22:02:30+00:00\n" +
                "    date:timestamp: 2025-02-11T22:02:30+00:00\n" +
                "    exif:DateTime: 2022:09:10 11:16:09\n" +
                "    exif:DateTimeOriginal: 2022:09:10 11:16:09\n" +
                "    exif:DigitalZoomRatio: 10000/10000\n" +
                "    exif:ExifOffset: 213\n" +
                "    exif:ExposureBiasValue: 0/100\n" +
                "    exif:ExposureMode: 0\n" +
                "    exif:ExposureProgram: 2\n" +
                "    exif:ExposureTime: 400/10000\n" +
                "    exif:Flash: 0\n" +
                "    exif:FNumber: 20000/10000\n" +
                "    exif:FocalLength: 460/100\n" +
                "    exif:FocalLengthIn35mmFilm: 25\n" +
                "    exif:ImageLength: 1800\n" +
                "    exif:ImageWidth: 4000\n" +
                "    exif:LightSource: 0\n" +
                "    exif:Make: samsung\n" +
                "    exif:MeteringMode: 3\n" +
                "    exif:Model: SM-A515F\n" +
                "    exif:PhotographicSensitivity: 500\n" +
                "    exif:Software: A515FXXU5FVE2\n" +
                "    exif:WhiteBalance: 0\n" +
                "    icc:copyright: Google Inc. 2016\n" +
                "    icc:description: sRGB\n" +
                "    jpeg:colorspace: 2\n" +
                "    jpeg:sampling-factor: 2x2,1x1,1x1\n" +
                "    signature: de619c030d4defacc962d8ff5646c11e3ded64a56e7898de9e373cc142fac99f\n" +
                "  Artifacts:\n" +
                "    verbose: true\n" +
                "  Tainted: False\n" +
                "  Filesize: 613836B\n" +
                "  Number pixels: 2.02876M\n" +
                "  Pixel cache type: Memory\n" +
                "  Pixels per second: 32.9546MP\n" +
                "  User time: 0.040u\n" +
                "  Elapsed time: 0:01.061\n" +
                "  Version: ImageMagick 7.1.1-43 Q16-HDRI x86_64 22550 https://imagemagick.org").when(spyService).getMetadataImageMagick(dir);
        doReturn(Map.of("width", 800L, "height", 600L)).when(spyService).getImageDimensions(dir);

        doNothing().when(spyService).createTransformedVersions(dir);

        spyService.processImage(imageId, dir);

        verify(imageRepository, times(1)).findById(imageId);

        verify(imageRepository, times(2)).save(image); // 2 volte: PROCESSING e PROCESSED

        assertNotEquals("dummyMetadata", image.getMetadata());
        assertEquals(800L, image.getWidth());
        assertEquals(600L, image.getHeight());
        assertEquals(ElaborationState.PROCESSED, image.getElaborationState());
    }

    @Test
    void testProcessImage_KO_NotFound() {
        Long imageId = 1L;
        String dir = "/dir/to/image";

        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            imageProcessorAsyncService.processImage(imageId, dir);
        });

        verify(imageRepository, times(1)).findById(imageId);

        verify(imageRepository, never()).save(any(Image.class));
    }

    @Test
    void testProcessImage_MetadataExtractionFailed() {
        Long imageId = 1L;
        String dir = "/path/to/image";

        ImageProcessorAsyncService spyService = Mockito.spy(imageProcessorAsyncService);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(new Image()));
        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doThrow(new RuntimeException("Metadata extraction failed"))
                .when(spyService).getMetadataImageMagick(dir);

        assertThrows(RuntimeException.class, () -> {
            spyService.processImage(imageId, dir);
        });

        verify(imageRepository, times(2)).save(any(Image.class));
    }
}
