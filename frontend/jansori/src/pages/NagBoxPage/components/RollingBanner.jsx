import React, { useState, useEffect } from 'react';
import CountBox from './CountBox';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import tw, { styled } from 'twin.macro';
import { getNagBoxStatistics } from '../../../apis/api/nag';

const RollingBanner = () => {
  const [counts, setCounts] = useState([]);
  const [loading, setLoading] = useState(true);

  const nagSentences = [
    {
      startSentence: '현재 총 ',
      expression: ' 명 ',
      endSentence: '이 사용 중이에요.',
    },
    {
      startSentence: '지금까지 ',
      expression: ' 개 ',
      endSentence: 'TODO가 완료되었어요.',
    },
    {
      startSentence: '현재 총 ',
      expression: ' 개 ',
      endSentence: '잔소리가 쌓였어요.',
    },
  ];
  const settings = {
    infinite: true, // 무한 루프
    vertical: true,
    speed: 1000, // 슬라이드 전환 속도 (밀리초)
    autoplay: true, // 자동 재생
    autoplaySpeed: 2000, // 자동 재생 간격 (밀리초)
    arrows: false,
  };

  useEffect(() => {
    (async () => {
      const data = await getNagBoxStatistics();
      setCounts(data);
      console.log(counts);
      setLoading(false);
    })();
  }, []);

  return (
    <div>
      <CenteredContainer>
        {loading ? (
          <LoadingMessage>Loading...</LoadingMessage>
        ) : (
          <Slider {...settings}>
            {nagSentences.map((nagSentence, index) => (
              <CountBox
                key={index}
                startSentence={nagSentence.startSentence}
                count={counts[Object.keys(counts)[index]] + nagSentence.expression}
                endSentence={nagSentence.endSentence}
              />
            ))}
          </Slider>
        )}
      </CenteredContainer>
    </div>
  );
};
export default RollingBanner;

const CenteredContainer = styled.div`
  ${tw`
  lg:w-2/5 
  md:w-3/5
  sm:w-4/5
  mx-auto
  mt-24
  mb-6
  `}
`;

const LoadingMessage = styled.p`
  ${tw`text-center text-gray-500 my-6 font-semibold text-4xl`}
`;
