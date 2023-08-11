import React from 'react';
import tw, { styled } from 'twin.macro';

const LoadingShimmer = () => {
  return (
    <LoadingPost>
      <LoadingWrapper>
        <LoadingProfile>
          <LoadingProfileImg />
          <div>
            <LoadingProfileNickname />
            <LoadingProfileNickname />
          </div>
        </LoadingProfile>
        <LoadingComment />
        <LoadingComment />
        <LoadingComment />
      </LoadingWrapper>
    </LoadingPost>
  );
};

const LoadingPost = styled.div`
  ${tw`p-8`}
  border-radius: 20px;
  margin: 10px auto;
  background-color: white;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
`;

const LoadingWrapper = styled.div`
  width: 0px;
  animation: fullView 0.5s forwards cubic-bezier(0.25, 0.46, 0.45, 0.94);
  @keyframes fullView {
    100% {
      width: 100%;
    }
  }
`;

const LoadingProfile = styled.div`
  display: flex;
`;

const LoadingProfileImg = styled.div`
  height: 48px;
  width: 48px;
  border-radius: 50%;
  animation: shimmer 2s infinite linear;
  background: linear-gradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);
  background-size: 1000px 100%;
  @keyframes shimmer {
    0% {
      background-position: -1000px 0;
    }
    100% {
      background-position: 1000px 0;
    }
  }
`;

const LoadingProfileNickname = styled.div`
  width: 100px;
  height: 10px;
  background: #777;
  margin: 10px 15px;
  animation: shimmer 2s infinite linear;
  background: linear-gradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);
  background-size: 1000px 100%;
  @keyframes shimmer {
    0% {
      background-position: -1000px 0;
    }
    100% {
      background-position: 1000px 0;
    }
  }
`;

const LoadingComment = styled.div`
  width: 100%;
  height: 10px;
  background: #777;
  margin-top: 20px;
  animation: shimmer 2s infinite linear;
  background: linear-gradient(to right, #eff1f3 4%, #e2e2e2 25%, #eff1f3 36%);
  background-size: 1000px 100%;
  @keyframes shimmer {
    0% {
      background-position: -1000px 0;
    }
    100% {
      background-position: 1000px 0;
    }
  }
`;

export default LoadingShimmer;
