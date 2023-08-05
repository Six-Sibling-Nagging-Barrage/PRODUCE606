import React, { useEffect, useRef } from 'react';
import { styled } from 'twin.macro';

const InfiniteScroll = (props) => {
  const { fetchNextPage, hasNextPage, isFetchingNextPage } = props;

  const target = useRef();

  const onIntersect = ([entry], observer) => {
    if (entry.isIntersecting && !isFetchingNextPage) {
      observer.unobserve(entry.target);
      fetchNextPage();
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer;
    if (target.current) {
      observer = new IntersectionObserver(onIntersect);
      observer.observe(target.current);
    }
    return () => observer && observer.disconnect();
  }, [onIntersect]);

  return (
    <>
      {hasNextPage && (
        <>
          {isFetchingNextPage ? (
            <div>피드를 불러오는 중...</div>
          ) : (
            <Target ref={target}></Target>
          )}
        </>
      )}
    </>
  );
};

const Target = styled.div`
  bottom: 0;
  height: 20px;
`;

export default InfiniteScroll;
