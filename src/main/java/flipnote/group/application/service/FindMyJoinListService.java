package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import flipnote.group.application.port.in.FindMyJoinListUseCase;
import flipnote.group.application.port.in.result.FindMyJoinListResult;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.join.JoinDomain;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindMyJoinListService implements FindMyJoinListUseCase {

	private final JoinRepositoryPort joinRepository;

	@Override
	public FindMyJoinListResult findMyJoinList(Long userId) {

		List<JoinDomain> joinList = joinRepository.findMyJoinList(userId);

		return new FindMyJoinListResult(joinList);
	}
}
