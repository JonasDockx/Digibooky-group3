package com.switchfully.digibookygroup3.domain.book;

import java.util.UUID;

public class Book {
        private final String id;
        private final String isbn;
        private final String title;
        private String authorId;
        private boolean isAvailable;
        private boolean isArchived;

        public Book(String isbn, String title, String authorId) {
                this.id = UUID.randomUUID().toString();
                this.isbn = isbn;
                this.title = title;
                this.authorId = authorId;
                this.isAvailable = true;
        }

        public String getId() {
                return id;
        }

        public String getIsbn() {
                return isbn;
        }

        public String getTitle() {
                return title;
        }

        public String getAuthorId() {
                return authorId;
        }
}
