package com.femcoders.tico.config;

import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.femcoders.tico.entity.Label;
import com.femcoders.tico.entity.Ticket;
import com.femcoders.tico.entity.User;
import com.femcoders.tico.enums.TicketPriority;
import com.femcoders.tico.enums.TicketStatus;
import com.femcoders.tico.enums.UserRole;
import com.femcoders.tico.repository.LabelRepository;
import com.femcoders.tico.repository.TicketRepository;
import com.femcoders.tico.repository.UserRepository;

@Configuration
@Profile("dev")
public class DevDataInitializer {

    @Bean
    CommandLineRunner initDevData(
            UserRepository userRepository,
            LabelRepository labelRepository,
            TicketRepository ticketRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.count() > 0) return;

            User admin = createUser("Admin Dev", "admin@tico.dev", "admin123", UserRole.ADMIN, passwordEncoder);
            User admin2 = createUser("Sara Admin", "sara@tico.dev", "admin123", UserRole.ADMIN, passwordEncoder);
            User emp1 = createUser("Carlos López", "carlos@tico.dev", "employee123", UserRole.EMPLOYEE, passwordEncoder);
            User emp2 = createUser("Ana García", "ana@tico.dev", "employee123", UserRole.EMPLOYEE, passwordEncoder);
            User emp3 = createUser("Pedro Martínez", "pedro@tico.dev", "employee123", UserRole.EMPLOYEE, passwordEncoder);

            userRepository.saveAll(List.of(admin, admin2, emp1, emp2, emp3));

            Label hardware  = createLabel("Hardware",  "#f28a2e", true);
            Label software  = createLabel("Software",  "#43598f", true);
            Label red       = createLabel("Red",       "#10b981", true);
            Label urgente   = createLabel("Urgente",   "#ef4444", true);
            Label email     = createLabel("Email",     "#f59e0b", true);
            Label inactiva  = createLabel("Obsoleta",  "#9ca3af", false);

            labelRepository.saveAll(List.of(hardware, software, red, urgente, email, inactiva));

     
            Ticket t1 = createTicket(
                "Ordenador no arranca",
                "Mi ordenador no enciende desde esta mañana. Tampoco responde al botón de encendido.",
                TicketStatus.OPEN, TicketPriority.HIGH,
                emp1, null, Set.of(hardware)
            );

            Ticket t2 = createTicket(
                "Error al instalar Office 365",
                "Al intentar instalar Office me aparece el error 0x80070005. He probado varias veces.",
                TicketStatus.IN_PROGRESS, TicketPriority.MEDIUM,
                emp2, admin, Set.of(software)
            );

            Ticket t3 = createTicket(
                "Sin acceso a internet en sala de reuniones",
                "Desde ayer ningún dispositivo conectado al WiFi de la sala B tiene acceso a internet.",
                TicketStatus.OPEN, TicketPriority.CRITICAL,
                emp3, null, Set.of(red, urgente)
            );

            Ticket t4 = createTicket(
                "No recibo emails externos",
                "Los emails enviados desde fuera de la empresa no me llegan. Los internos sí funcionan.",
                TicketStatus.IN_PROGRESS, TicketPriority.HIGH,
                emp1, admin2, Set.of(email, software)
            );

            Ticket t5 = createTicket(
                "Impresora no reconocida",
                "La impresora de la planta 2 no aparece en ningún ordenador tras el reinicio del servidor.",
                TicketStatus.CLOSED, TicketPriority.LOW,
                emp2, admin, Set.of(hardware)
            );

            Ticket t6 = createTicket(
                "Actualización de Windows bloqueada",
                "Windows Update lleva 3 días descargando la misma actualización y no termina.",
                TicketStatus.OPEN, TicketPriority.LOW,
                emp3, null, Set.of(software)
            );

            ticketRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6));

            System.out.println(">>> Dev data created:");
            System.out.println(">>> ADMIN    → admin@tico.dev / admin123");
            System.out.println(">>> ADMIN    → sara@tico.dev / admin123");
            System.out.println(">>> EMPLOYEE → carlos@tico.dev / employee123");
            System.out.println(">>> EMPLOYEE → ana@tico.dev / employee123");
            System.out.println(">>> EMPLOYEE → pedro@tico.dev / employee123");
        };
    }

    private User createUser(String name, String email, String password, UserRole role, PasswordEncoder encoder) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setRoles(Set.of(role));
        user.setIsActive(true);
        return user;
    }

    private Label createLabel(String name, String color, boolean active) {
        Label label = new Label();
        label.setName(name);
        label.setColor(color);
        label.setIsActive(active);
        return label;
    }

    private Ticket createTicket(String title, String description,
            TicketStatus status, TicketPriority priority,
            User createdBy, User assignedTo, Set<Label> labels) {
        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(status);
        ticket.setPriority(priority);
        ticket.setCreatedBy(createdBy);
        ticket.setAssignedTo(assignedTo);
        ticket.setLabels(labels);
        return ticket;
    }
}