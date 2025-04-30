package com.moviebooking.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.entity.User;
import com.moviebooking.app.repository.MovieRepository;
import com.moviebooking.app.repository.TicketRepository;
import com.moviebooking.app.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initMovies(MovieRepository movieRepository, TicketRepository ticketRepository) {
        return (args) -> {
            // Optional: Clear old data
            movieRepository.deleteAll();
//            ticketRepository.deleteAll();

            // Create sample movies with theatre
            Movie m1 = new Movie("Inception", "PVR Cinemas", 5);
            Movie m2 = new Movie("Inception", "INOX", 5);
            Movie m3 = new Movie("Interstellar", "PVR Cinemas", 100);
            Movie m4 = new Movie("Interstellar", "INOX", 100);

            movieRepository.save(m1);
            movieRepository.save(m2);
            movieRepository.save(m3);
            movieRepository.save(m4);

            System.out.println("✅ Sample movies initialized in MongoDB.");
        };
    }

    @Bean
    CommandLineRunner intiAdminUsers(UserRepository userRepository){
        return (args) -> {
            User user = User.builder()
                    .role("ADMIN")
                    .password("admin123")
                    .loginId("adminId")
                    .email("admin@gmail.com")
                    .contactNumber("987653210")
                    .firstName("Bhupesh")
                    .lastName("Pattanaik")
                    .build();
            userRepository.save(user);
        };
    }
//    @Bean
//    CommandLineRunner intiTestUsers(UserRepository userRepository){
//        return (args) -> {
//            User user = User.builder()
//                    .role("USER")
//                    .password("password123")
//                    .loginId("johndoe123")
//                    .email("admin@gmail.com")
//                    .contactNumber("987653210")
//                    .firstName("Bhupesh")
//                    .lastName("Pattanaik")
//                    .build();
//            userRepository.save(user);
//        };
//    }
}