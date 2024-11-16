package cotato.backend.domains.post;

import static cotato.backend.common.exception.ErrorCode.*;

import cotato.backend.domains.post.dto.PostDTO;
import cotato.backend.domains.post.exception.PostErrorCode;
import cotato.backend.domains.post.exception.PostException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {
	private static final long INCREASE_VIEWS_AMOUNT = 1L;

	private final PostRepository postRepository;

	// 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
	@Transactional
	public void saveEstatesByExcel(final String filePath) {
		try {
			// 엑셀 파일을 읽어 데이터 프레임 형태로 변환
			List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
					.map(PostDTO::toPostDTO)
					.map(Post::toPost)
					.toList();

			postRepository.saveAll(posts);
		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}
	}

	// 전달받은 값으로 Post 엔티티로 변환하고 저장
	@Transactional
	public void savePost(final PostDTO postDTO) {
		postRepository.save(Post.toPost(postDTO));
	}

	// 전달받은 id 값으로 Post 엔티티를 조회하고, PostDTO로 변환해 반환
	// 조회 성공 시, views 값을 1 늘려줌
	@Transactional
	public PostDTO findPostById(final long id) {
		Post foundPost = postRepository.findById(id).orElseThrow(
				() -> PostException.from(PostErrorCode.NOT_EXIST)
		);

		foundPost.setViews(foundPost.getViews() + INCREASE_VIEWS_AMOUNT);

		return PostDTO.toPostDTO(foundPost);
	}
}
