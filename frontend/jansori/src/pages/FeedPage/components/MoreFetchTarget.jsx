import React, { useRef, useEffect } from 'react';
import { styled } from 'twin.macro';

const MoreFetchTarget = (props) => {
  const { items, setCursor, pageSize } = props;

  const fetchTriggerRef = useRef(null);
  const fetchObserver = new IntersectionObserver(([{ isIntersecting }]) => {
    if (isIntersecting) {
      setCursor((prev) => {
        // 타겟이 관측되면 커서를 갱신
        return prev + pageSize;
      });
    }
  });

  useEffect(() => {
    let observerRef = null;
    fetchObserver.observe(fetchTriggerRef.current);
    observerRef = fetchTriggerRef.current;
    return () => {
      if (observerRef) {
        fetchObserver.unobserve(observerRef);
      }
    };
  }, [items]);

  return <TargetBox ref={fetchTriggerRef} />;
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
