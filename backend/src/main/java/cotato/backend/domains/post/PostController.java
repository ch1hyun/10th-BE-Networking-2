package cotato.backend.domains.post;

import cotato.backend.domains.post.dto.PostDTO;
import cotato.backend.domains.post.dto.request.SavePostRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(final @RequestBody @Valid SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.path());

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping
	public ResponseEntity<DataResponse<Void>> savePost(final @RequestBody @Valid SavePostRequest savePostRequest) {
		postService.savePost(PostDTO.toPostDTO(savePostRequest));

		return ResponseEntity.ok(DataResponse.ok());
	}
}
