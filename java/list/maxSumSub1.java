private static int maxSumRec(int[] a, int left, int right) {
	if (left == right)
		if(a[left] > 0)
			return a[left];
		else
			return 0;

	int center = (left + right) / 2;
	int maxLeftSum = maxSumRec(a, left, center);
	int maxRightSum = maxSumRec(a, center + 1, right);

	int maxLeftBorderSum = 0, leftBorderSum = 0;
	for(int i = center; i >= left; i--) {
		leftBorderSum += a[i];
		if (leftBorderSum > maxLeftBorderSum)
			maxLeftBorderSum = leftBorderSum;
	}

	int maxRightBorderSum = 0, rightBorderSum = 0;
	for (int i = center + 1; i <= right; i++) {
		rightBorderSum += a[i];
		if (rightBorderSum > maxRightBorderSum)
			maxRightBorderSum = rightBorderSum;
	}

	return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);

}

public static int maxSubSum1(int[] a) {
	return maxSumRec(a, 0, a.length - 1);
}