import React, { useEffect, useState } from 'react';
import NagItem from './NagItem';
import { styled } from 'twin.macro';
import { MasonryInfiniteGrid } from '@egjs/react-infinitegrid';
import { getMyNagList } from '../../../apis/api/nag';

const NagHistory = (props) => {
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
    // 사용자가 보낸 잔소리 조회 api 호출
    const data = await getMyNagList({ cursor: nextCursor, pageSize });
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
      {items && items.map((nag, index) => <NagItem key={index} nag={nag} />)}
    </MasonryInfiniteGrid>
  );
};

export default NagHistory;

const dummyData = {
  nags: [
    {
      nagId: 1,
      content:
        '니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 4,
      tagName: '개발',
      deliveredCount: 1,
    },
    {
      nagId: 2,
      content: '니 코드가 개발새발인데 놀고싶어?',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 4,
      tagName: '개발',
      deliveredCount: 4,
    },
    {
      nagId: 3,
      content: '답답하다~',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 2,
      tagName: '코딩',
      deliveredCount: 5,
    },
    {
      nagId: 4,
      content: '답없누',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 2,
      tagName: '코딩',
      deliveredCount: 2,
    },
    {
      nagId: 5,
      content: '돼지되겠어',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 10,
      tagName: '개발',
      deliveredCount: 1,
    },
    {
      nagId: 6,
      content:
        '니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?니 코드가 개발새발인데 놀고싶어?',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 4,
      tagName: '개발',
      deliveredCount: 1,
    },
    {
      nagId: 7,
      content: '니 코드가 개발새발인데 놀고싶어?',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 4,
      tagName: '개발',
      deliveredCount: 4,
    },
    {
      nagId: 8,
      content: '답답하다~',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 2,
      tagName: '코딩',
      deliveredCount: 5,
    },
    {
      nagId: 9,
      content: '답없누',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 2,
      tagName: '코딩',
      deliveredCount: 2,
    },
    {
      nagId: 10,
      content: '돼지되겠어',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 10,
      tagName: '개발',
      deliveredCount: 1,
    },
    {
      nagId: 11,
      content: '돼지되겠어',
      createAt: '2023-08-03T10:58:58.909891',
      likeCount: 10,
      tagName: '개발',
      deliveredCount: 1,
    },
  ],
  hasNext: true,
  nextCursor: 106,
};
