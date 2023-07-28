import React, { useRef, useEffect } from 'react';
import { styled } from 'twin.macro';

const MoreFetchTarget = (props) => {
  const { items, setCursor } = props;

  const fetchTrigger = useRef(null);
  const fetchObserver = new IntersectionObserver(([{ isIntersecting }]) => {
    if (isIntersecting)
      setCursor((prev) => {
        // items.at(-1).id
        return prev + 1; // 임시 데이터
      });
  });

  useEffect(() => {
    let observerRef = null;
    fetchObserver.observe(fetchTrigger.current);
    observerRef = fetchTrigger.current;
    return () => {
      if (observerRef) {
        console.log('관측됨');
        console.log(items);
        fetchObserver.unobserve(observerRef);
      }
    };
  }, [items]);

  return <TargetBox ref={fetchTrigger} />;
};

const TargetBox = styled.div`
  bottom: 0;
  left: 0;
  right: 0;
  margin-top: 10px;
  text-align: center;
  line-height: 28px;
`;

export default MoreFetchTarget;
