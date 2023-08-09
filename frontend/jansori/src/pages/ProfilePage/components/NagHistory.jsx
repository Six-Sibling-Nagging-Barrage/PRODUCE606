import React, { useEffect, useState } from 'react';
import NagItem from './NagItem';
import { styled } from 'twin.macro';
import { MasonryInfiniteGrid } from '@egjs/react-infinitegrid';
import { getMemberNagList, getMyNagList } from '../../../apis/api/nag';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../../states/user';

const NagHistory = (props) => {
  const { isMine, id } = props;

  const [items, setItems] = useState([]);
  const [nextCursor, setNextCursor] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [hasNext, setHasNext] = useState(true);

  const pageSize = 10;

  useEffect(() => {
    handleGetNags();
  }, []);

  const handleGetNags = async () => {
    if (isLoading) return; // 이미 로딩 중이면 중복 요청 방지
    if (!hasNext) return;

    setIsLoading(true);
    let data;
    // 내가 보낸 잔소리 목록 조회 api
    if (isMine) data = await getMyNagList({ cursor: nextCursor, pageSize });
    // 다른 사람이 보낸 잔소리 목록 조회 api
    else
      data = await getMemberNagList({
        memberId: id,
        cursor: nextCursor,
        pageSize,
      });
    console.log(data);
    setItems((prev) => [...prev, ...data.data.nags]);
    setNextCursor(data.data.nextCursor);
    setHasNext(data.data.hasNext);
    setIsLoading(false);
  };

  return (
    <MasonryInfiniteGrid
      align="center"
      gap={10}
      onRequestAppend={handleGetNags}
      loading={isLoading}
    >
      {items &&
        items.map((nag, index) => (
          <NagItem key={index} isMine={isMine} nag={nag} />
        ))}
    </MasonryInfiniteGrid>
  );
};

export default NagHistory;
