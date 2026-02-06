# FestiCore

A comprehensive Java-based festival management system for handling tickets, passes, activities, reservations, and PDF ticket generation.

## Description

FestiCore is a full-featured festival booking and management platform that allows users to:
- Register and manage user accounts
- Browse festival programs with various events (concerts, activities, workshops)
- Purchase tickets, passes, and activities
- Generate official PDF tickets upon successful purchase
- Track reservation history
- Manage payment information securely

The system provides a robust backend service with proper exception handling, logging, data persistence (JSON), and PDF generation capabilities.

## Features

- **User Management**: Registration, login, and account management
- **Festival Program**: Browse concerts, activities, and stages
- **Booking System**: Reserve tickets, passes, and activities with real-time stock management
- **PDF Generation**: Automatic ticket generation with detailed reservation information
- **Payment Processing**: Secure card payment with save option
- **Logging System**: Comprehensive logging for debugging and monitoring
- **Data Persistence**: JSON-based data storage for users and reservations
- **Multiple Reservation Types**:
  - Tickets (1-day, 3-day, festival passes)
  - Activities (workshops, shows with artist information)
  - Passes (VIP, Standard, etc.)

## Installation

### Requirements
- Java 25 or higher
- Maven 3.8+
- Windows/Linux/macOS

### Setup

1. Clone the repository:
```bash
git clone https://github.com/the-pek/FestiCore.git
cd festicore
```

2. Build the project:
```bash
mvn clean install
```

3. Configure logging (optional):
Edit `src/main/resources/application.properties`:
```properties
log.filename=festicore_log.log
log.level=INFO
```

4. Run the application:
```bash
mvn exec:java -Dexec.mainClass="org.festicore.festicore.Main"
```

## Usage

### Starting the Application
```bash
java -jar target/Festicore-1.0-SNAPSHOT.jar
```

### User Flow

1. **Registration**: Create a new account with email, phone, and password validation
2. **Login**: Access your account with email and password
3. **Browse Festival**: View all available concerts, activities, and passes
4. **Book Reservation**: 
   - Select an event/ticket
   - Enter payment information
   - Receive PDF ticket via email/locally
5. **Manage Account**: View history, search reservations, download tickets

### Account Commands
- `-a`: Show reservation history
- `-r`: Find a specific reservation
- `-b`: Buy a new reservation
- `-p`: Show festival program
- `-h`: Help menu
- `-q`: Logout

## Project Structure

```
festicore/
├── src/
│   ├── main/java/org/festicore/festicore/
│   │   ├── Activity.java
│   │   ├── Concert.java
│   │   ├── Festival.java
│   │   ├── Main.java
│   │   ├── Pass.java
│   │   ├── Reservation.java
│   │   ├── Stage.java
│   │   ├── Tickets.java
│   │   ├── User.java
│   │   ├── Enumeration/
│   │   │   ├── ActivityType.java
│   │   │   ├── ArtistName.java
│   │   │   ├── PassType.java
│   │   │   └── TicketType.java
│   │   ├── Exceptions/
│   │   │   ├── FestivalException.java
│   │   │   ├── ReservationException.java
│   │   │   └── SaveException.java
│   │   └── service/
│   │       ├── BookingService.java
│   │       ├── JsonDataManager.java
│   │       ├── PDFGenerator.java
│   │       └── LogManager.java
│   ├── test/java/org/esiea/festicore/
│   │   ├── OrgaTest.java
│   │   ├── ReservationTest.java
│   │   └── UserTest.java
│   └── resources/
│       └── application.properties
├── data/
│   ├── users.json
│   └── reservations.json
└── pom.xml
```

## Dependencies

- **Jackson**: JSON serialization/deserialization
- **iText (com.lowagie)**: PDF generation
- **JUnit Jupiter**: Unit testing
- **Java Logging**: Built-in logging framework

## Testing

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=OrgaTest
```

## Architecture

### Core Classes

- **User**: Manages user accounts, registration, login, and booking
- **Festival**: Orchestrates the festival with stages, concerts, and activities
- **Reservation**: Base class for all bookable items (Tickets, Activities, Passes)
- **PDFGenerator**: Generates official PDF tickets with reservation details
- **BookingService**: Handles booking logic and inventory management
- **LogManager**: Centralized logging system
- **JsonDataManager**: Persists data to JSON files

### Exception Handling

- `FestivalException`: Festival-related errors
- `ReservationException`: Booking/reservation errors
- `SaveException`: Data persistence errors

## PDF Ticket Features

Generated PDF tickets include:
- Attendee name
- Reservation type (Ticket/Activity/Pass)
- Validity date and price
- Type-specific details:
  - **Tickets**: Duration (1-day, 3-day, etc.)
  - **Activities**: Artist name, activity type, start time, duration
  - **Passes**: Pass type and access level

## Contributing

1. Create a feature branch (`git checkout -b feature/AmazingFeature`)
2. Commit changes (`git commit -m 'Add some AmazingFeature'`)
3. Push to branch (`git push origin feature/AmazingFeature`)
4. Open a merge request

## Authors

- **Pierre-Edwin KENGNE KENGNE** - Initial development

## Support

For issues or questions:
- Check existing issues on GitLab
- Contact the development team
- Review logs in `festicore_log.log`

## Roadmap

- [ ] Email notification system for ticket delivery
- [ ] Advanced analytics and reporting
- [ ] Integration with external payment gateways
- [ ] Real-time inventory management
- [ ] QR code generation for tickets
- [ ] Festival seating chart visualization
