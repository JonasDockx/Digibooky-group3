package com.switchfully.digibookygroup3.domain.book;

import java.util.UUID;

public class Book {
        private final String id;
        private final String ISBN;
        private final String title;
        private String authorId;
        private boolean isAvailable;
        private boolean isArchived;

        public Book(String ISBN, String title, String authorId) {
                this.id = UUID.randomUUID().toString();
                this.ISBN = ISBN;
                this.title = title;
                this.authorId = authorId;
                this.isAvailable = true;
        }

        public String getId() {
                return id;
        }

        public String getISBN() {
                return ISBN;
        }

        public String getTitle() {
                return title;
        }

        public String getAuthorId() {
                return authorId;
        }
}
