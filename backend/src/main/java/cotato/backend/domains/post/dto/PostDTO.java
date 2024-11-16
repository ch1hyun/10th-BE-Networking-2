package cotato.backend.domains.post.dto;

import java.util.Map;

public record PostDTO (
        Long id,
        String title,
        String content,
        String name,
        Long views
) {
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String NAME = "name";
    private static final long DEFAULT_VIEWS = 0L;

    public static PostDTO toPostDTO(final Map<String, String> parsedData) {
        return new PostDTO(
                null,
                parsedData.get(TITLE),
                parsedData.get(CONTENT),
                parsedData.get(NAME),
                DEFAULT_VIEWS
        );
    }
}
