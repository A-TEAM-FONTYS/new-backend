package nl.teama.server.controller.enums;

public enum DataResponse {
    INVALID_PARAMS("Invalid parameters"),
    UNEXPECTED_ERROR("A unexpected error occurred. Try again later");

    private final String text;

    DataResponse(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
