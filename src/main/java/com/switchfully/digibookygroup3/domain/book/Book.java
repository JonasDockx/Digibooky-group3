package com.switchfully.digibookygroup3.domain.book;

import java.util.UUID;

public class Book {
        private final String id;
        private String isbn;
        private String title;
        private String authorId;
        private boolean isAvailable;
        private boolean isArchived;

        public void setSummary(String summary) {
                this.summary = summary;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public void setAuthorId(String authorId) {
                this.authorId = authorId;
        }

        private String summary;

        public Book(String isbn, String title, String authorId, String summary) {
                this.id = UUID.randomUUID().toString();
                this.isbn = isbn;
                this.title = title;
                this.authorId = authorId;
                this.isAvailable = true;
                this.isArchived = false;
                this.summary = summary;
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

        public String getSummary() {
                return summary;
        }
}
