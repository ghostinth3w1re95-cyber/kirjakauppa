# Bookstore REST API Testing Guide

## Changes Made

### Task 1: Custom Spring REST Services

#### 1. Added @JsonIgnore to Book Entity
- File: [Book.java](src/main/java/oma/kirja/kauppa/domain/Book.java)
- Added `@JsonIgnore` annotation to the `category` field to prevent circular references in JSON serialization

#### 2. Created BookRestController
- File: [BookRestController.java](src/main/java/oma/kirja/kauppa/web/BookRestController.java)
- Created a new REST controller with base path `/rest/books`
- Implemented two endpoints:
  - `GET /rest/books` - Returns all books as JSON
  - `GET /rest/books/{id}` - Returns a single book by ID as JSON

### Task 2: Spring Data REST Configuration

#### 1. Added Dependency in pom.xml
- Added `spring-boot-starter-data-rest` dependency

#### 2. Updated application.properties
- Configured Spring Data REST base path to `/api`
- Set default page size to 20

---

## Testing with Postman

### Custom REST Endpoints (Task 1)

#### 1. GET All Books
```
Method: GET
URL: http://localhost:8080/rest/books
Expected Response: JSON array of all books
```

**Example Response:**
```json
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "price": 12.99
  },
  {
    "id": 2,
    "title": "1984",
    "author": "George Orwell",
    "price": 13.99
  }
]
```

#### 2. GET Single Book by ID
```
Method: GET
URL: http://localhost:8080/rest/books/1
Expected Response: JSON object of the book with ID 1
```

**Example Response:**
```json
{
  "id": 1,
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "price": 12.99
}
```

---

### Spring Data REST Endpoints (Task 2)

Spring Data REST automatically exposes repository methods as REST endpoints under the base path `/api`.

#### 1. GET All Books (Spring Data REST)
```
Method: GET
URL: http://localhost:8080/api/books
Expected Response: HAL JSON format with books and pagination
```

**Example Response:**
```json
{
  "_embedded": {
    "books": [
      {
        "id": 1,
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "price": 12.99,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/books/1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/books{?page,size,sort}",
      "templated": true
    }
  },
  "page": {
    "size": 20,
    "totalElements": 2,
    "totalPages": 1,
    "number": 0
  }
}
```

#### 2. GET Single Book (Spring Data REST)
```
Method: GET
URL: http://localhost:8080/api/books/1
```

#### 3. POST - Create New Book (Spring Data REST)
```
Method: POST
URL: http://localhost:8080/api/books
Content-Type: application/json

Body:
{
  "title": "New Book Title",
  "author": "Author Name",
  "price": 19.99
}
```

#### 4. Search Operations (Spring Data REST)
```
Method: GET
URL: http://localhost:8080/api/books?search=
```

---

## Key Differences

| Aspect | Custom REST (/rest/books) | Spring Data REST (/api/books) |
|--------|---------------------------|-------------------------------|
| Path | /rest/books | /api/books |
| Format | Plain JSON | HAL JSON (hypermedia) |
| Auto CRUD | No (custom code) | Yes |
| Category Field | Excluded (@JsonIgnore) | Included (circular) |
| Pagination | No | Yes (default page size 20) |

