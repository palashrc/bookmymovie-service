package com.bookmymovie.theater.service;

import com.bookmymovie.core.error.CoversionException;
import com.bookmymovie.core.error.Error;
import com.bookmymovie.theater.constant.ExceptionConstants;
import com.bookmymovie.theater.converter.CityConverter;
import com.bookmymovie.theater.helper.StatusMapper;
import com.bookmymovie.theater.model.City;
import com.bookmymovie.theater.model.CityRequest;
import com.bookmymovie.theater.model.CityResponse;
import com.bookmymovie.theater.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityConverter mockCityConverter;
    @Mock
    private CityRepository mockCityRepository;
    @Mock
    private StatusMapper mockStatusMapper;

    @InjectMocks
    private CityService cityServiceUnderTest;

    @Test
    void testSaveCity() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.City city = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityConverter.convertModelToEntity(City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build())).thenReturn(city);

        // Configure CityRepository.save(...).
        final com.bookmymovie.theater.entity.City city1 = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityRepository.save(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        // Configure CityConverter.convertEntityToModel(...).
        final City city2 = City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build();
        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city2);

        // Run the test
        final CityResponse result = cityServiceUnderTest.saveCity(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testSaveCity_CityConverterConvertModelToEntityThrowsCoversionException() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityConverter.convertModelToEntity(City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.saveCity(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveCity_CityConverterConvertEntityToModelThrowsCoversionException() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityConverter.convertModelToEntity(...).
        final com.bookmymovie.theater.entity.City city = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityConverter.convertModelToEntity(City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build())).thenReturn(city);

        // Configure CityRepository.save(...).
        final com.bookmymovie.theater.entity.City city1 = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityRepository.save(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.saveCity(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCity() {
        // Setup
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findAll(...).
        final Iterable<com.bookmymovie.theater.entity.City> cities = List.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findAll()).thenReturn(cities);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCity();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCity_CityRepositoryReturnsNoItems() {
        // Setup
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCity();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityById() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Configure CityConverter.convertEntityToModel(...).
        final City city1 = City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build();
        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityById(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityById_CityRepositoryReturnsAbsent() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityById(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityById_CityConverterThrowsCoversionException() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityById(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityByName() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findByCityName(...).
        final Optional<List<com.bookmymovie.theater.entity.City>> cities = Optional.of(
                List.of(com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build()));
        when(mockCityRepository.findByCityName("cityName")).thenReturn(cities);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByName(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityByName_CityRepositoryReturnsAbsent() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findByCityName("cityName")).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByName(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityByName_CityRepositoryReturnsNoItems() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findByCityName("cityName")).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByName(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateCityOperational() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Configure CityRepository.save(...).
        final com.bookmymovie.theater.entity.City city1 = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityRepository.save(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        // Configure CityConverter.convertEntityToModel(...).
        final City city2 = City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build();
        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city2);

        // Run the test
        final CityResponse result = cityServiceUnderTest.updateCityOperational(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testUpdateCityOperational_CityRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.updateCityOperational(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateCityOperational_CityConverterThrowsCoversionException() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Configure CityRepository.save(...).
        final com.bookmymovie.theater.entity.City city1 = com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build();
        when(mockCityRepository.save(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.updateCityOperational(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityForUser() {
        // Setup
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findAll(...).
        final Iterable<com.bookmymovie.theater.entity.City> cities = List.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findAll()).thenReturn(cities);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityForUser();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityForUser_CityRepositoryReturnsNoItems() {
        // Setup
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityForUser();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityByIdForUser() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        // Configure CityConverter.convertEntityToModel(...).
        final City city1 = City.builder()
                .cityId(0L)
                .cityName("cityName")
                .operational(false)
                .build();
        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenReturn(city1);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByIdForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityByIdForUser_CityRepositoryReturnsAbsent() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByIdForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityByIdForUser_CityConverterThrowsCoversionException() throws Exception {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findById(...).
        final Optional<com.bookmymovie.theater.entity.City> city = Optional.of(
                com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build());
        when(mockCityRepository.findById(0L)).thenReturn(city);

        when(mockCityConverter.convertEntityToModel(com.bookmymovie.theater.entity.City.builder()
                .operational(false)
                .build())).thenThrow(CoversionException.class);
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.CONVERSION_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByIdForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityByNameForUser() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();

        // Configure CityRepository.findByCityName(...).
        final Optional<List<com.bookmymovie.theater.entity.City>> cities = Optional.of(
                List.of(com.bookmymovie.theater.entity.City.builder()
                        .operational(false)
                        .build()));
        when(mockCityRepository.findByCityName("cityName")).thenReturn(cities);

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByNameForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockStatusMapper).mapSuccessCodeMsg(CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build());
    }

    @Test
    void testGetCityByNameForUser_CityRepositoryReturnsAbsent() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findByCityName("cityName")).thenReturn(Optional.empty());
        when(mockStatusMapper.mapErrorCodeMsg(ExceptionConstants.DATASTORE_EXCEPTION_TYPE))
                .thenReturn(Error.builder().build());

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByNameForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCityByNameForUser_CityRepositoryReturnsNoItems() {
        // Setup
        final CityRequest cityRequest = CityRequest.builder()
                .city(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build())
                .build();
        final CityResponse expectedResult = CityResponse.builder()
                .cities(List.of(City.builder()
                        .cityId(0L)
                        .cityName("cityName")
                        .operational(false)
                        .build()))
                .errors(List.of(Error.builder().build()))
                .build();
        when(mockCityRepository.findByCityName("cityName")).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final CityResponse result = cityServiceUnderTest.getCityByNameForUser(cityRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
