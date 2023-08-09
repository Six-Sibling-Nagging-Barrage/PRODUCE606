export const isExpired = (exp) => {
  return new Date(exp) < new Date();
};
