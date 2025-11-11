# Deals API

A simple Java Spring Boot service that provides deal availability and information across restaurants.

## Endpoints

### 1. `GET /api/deals`

Returns all available deals, optionally filtered by time of day.

#### Query Parameters

| Name        | Type     | Format           | Description                                      |
|-------------|----------|------------------|--------------------------------------------------|
| `timeOfDay` | `String` | `h:mma` or `H:mm` | Optional. Filters deals active at the given time.|

#### Time Format Examples
- `9:00am`
- `9:00AM`
- `14:30`

#### Example Requests
```http
GET /api/deals
GET /api/deals?timeOfDay=9:00am
GET /api/deals?timeOfDay=14:30
```

#### Response
Returns a list of available deals at the specified time. If no timeOfDay is provided, all deals are returned.

### 2. `GET /api/deals/peak-time`

#### Response
```json
{
  "peakTimeStart": "18:00",
  "peakTimeEnd": "21:00"
}
```
## Running the Service
```bash
./mvnw spring-boot:run
```

## Notes
- Deals without explicit start/end times default to the restaurant's open/close hours.
- Restaurants without specified hours are assumed to be 24 hours.
- Time parsing is flexible but must match either h:mma or H:mm formats.
- Peak time is computed using a sweep line algorithm for performance.

## Project Structure
```markdown
src/
├── main/
│   ├── java/
│   │   └── org.eatclub.deals/
│   │       ├── builder/           # Maps Deal and Restaurant data into domain model objects
│   │       ├── client/            # Integrates with external restaurant data sources
│   │       ├── controller/        # REST controllers for /api/deals endpoints
│   │       ├── exception/         # Custom exceptions and error handling
│   │       ├── model/             # Domain models: Deal, Restaurant, Event, etc.
│   │       ├── response/          # DTOs for API responses to consumers
│   │       ├── service/           # Business logic for deal filtering and peak time analysis
│   │       ├── util/              # Helper utilities (e.g., time parsing, formatting)
│   │       └── DealsApplication.java  # Spring Boot application entry point
│   └── resources/
│       └── application.yml        # Configuration for external APIs
```

### 3. Schema Design
A basic PostgreSQL schema for storing deals and restaurant information, however some things to take note:
- good if we are keeping a relatively rigid data structure
- normalized structure eg. no repeating cuisine types
- if we do frequent updates to multiple tables then SQL excels
- however if schema frequently changes (adding conditions to deals etc) a NoSQL solution may be better
- normalization may be a hindrance with joins (front end fetching restaurant deals in one go)

```sql
-- Restaurants 
CREATE TABLE restaurants (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    address TEXT,
    suburb TEXT,
    image_link TEXT,
    open TIME, -- Times requirng some level of normalization to include seconds as this isn't in the Restaurant API
    close TIME
);

-- Cuisines
CREATE TABLE cuisines (
    id SERIAL PRIMARY KEY, -- Auto incrementing ID for Cuisines
    restaurant_id UUID REFERENCES restaurants(id) ON DELETE CASCADE,
    cuisine TEXT NOT NULL
);

-- Deals
CREATE TABLE deals (
    id UUID PRIMARY KEY,
    restaurant_id UUID REFERENCES restaurants(id) ON DELETE CASCADE,
    discount INTEGER,
    dine_in BOOLEAN DEFAULT FALSE,
    lightning BOOLEAN DEFAULT FALSE,
    start TIME,
    end TIME,
    qty_left INTEGER DEFAULT 0
);
```