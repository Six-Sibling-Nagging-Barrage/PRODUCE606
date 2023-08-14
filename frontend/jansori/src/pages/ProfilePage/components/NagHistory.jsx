import React, { useEffect, useState } from 'react';
import NagItem from './NagItem';
import { styled } from 'twin.macro';
import { MasonryInfiniteGrid } from '@egjs/react-infinitegrid';
import { getMemberNagList, getMyNagList } from '../../../apis/api/nag';
import StartButton from '../../MainPage/components/StartButton';
import { personas } from '../../../constants/persona';
import LoadingShimmer from '../../../components/UI/LoadingShimmer';

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
    setItems((prev) => [...prev, ...data.data.nags]);
    setNextCursor(data.data.nextCursor);
    setHasNext(data.data.hasNext);
    setIsLoading(false);
    console.log(items);
  };

  const randomIndex = () => {
    return Math.floor(Math.random() * 6);
  };

  return (
    <>
      {isLoading ? (
        <>
          <LoadingShimmer />
        </>
      ) : (
        <>
          {items && items.length > 0 ? (
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
          ) : (
            <StartButtonWrapper>
              <PersonaImg>
                <img src={personas[randomIndex()].gifUrl} />
              </PersonaImg>
              <Message>아직 잔소리를 보낸 적이 없어요!</Message>
              <StartButton nagCount={-1}></StartButton>
            </StartButtonWrapper>
          )}
        </>
      )}
    </>
  );
};

const PersonaImg = styled.div`
  width: fit-content;
  margin: 0 auto;
  & > img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 50%;
  }
`;

const Message = styled.div`
  padding-top: 10px;
  padding-bottom: 20px;
`;

const StartButtonWrapper = styled.div`
  margin: 20px auto;
`;

export default NagHistory;
