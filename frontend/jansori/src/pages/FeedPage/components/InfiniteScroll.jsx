import React, { useEffect, useRef } from 'react';
import { styled } from 'twin.macro';
import LoadingShimmer from '../../../components/UI/LoadingShimmer';

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
            <LoadingShimmer />
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
