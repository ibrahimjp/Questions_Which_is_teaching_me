import java.util.*;

public class Solution {

    static class SegmentTree {
        private int[] tree;
        private int size;

        public SegmentTree(int n) {
            this.size = n;
            this.tree = new int[4 * n];
        }

        public void update(int idx, int val) {
            update(0, 0, size - 1, idx, val);
        }

        private void update(int node, int l, int r, int idx, int val) {
            if (l == r) {
                tree[node] += val;
                return;
            }

            int mid = (l + r) / 2;
            if (idx <= mid) {
                update(2 * node + 1, l, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, r, idx, val);
            }

            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }

        public int query(int left, int right) {
            return query(0, 0, size - 1, left, right);
        }

        private int query(int node, int l, int r, int ql, int qr) {
            if (qr < l || r < ql) return 0;
            if (ql <= l && r <= qr) return tree[node];

            int mid = (l + r) / 2;
            int leftSum = query(2 * node + 1, l, mid, ql, qr);
            int rightSum = query(2 * node + 2, mid + 1, r, ql, qr);
            return leftSum + rightSum;
        }
    }

    public int[] resultArray(int[] nums) {
        int n = nums.length;
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        SegmentTree t1 = new SegmentTree(n + 1);
        SegmentTree t2 = new SegmentTree(n + 1);

        List<int[]> all = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            all.add(new int[]{nums[i], i});
        }

        all.sort((x, y) -> {
            if (x[0] != y[0]) return Integer.compare(x[0], y[0]);
            return Integer.compare(x[1], y[1]);
        });

        int[] mapped = new int[n];
        for (int i = 0; i < n; i++) {
            mapped[all.get(i)[1]] = i;
        }

        a.add(nums[0]);
        b.add(nums[1]);
        t1.update(mapped[0], 1);
        t2.update(mapped[1], 1);

        for (int i = 2; i < n; i++) {
            int idx = mapped[i];
            int gtIndex = upperBound(all, new int[]{nums[i], n});
            int c1 = t1.query(gtIndex, n);
            int c2 = t2.query(gtIndex, n);

            if (c1 > c2) {
                a.add(nums[i]);
                t1.update(idx, 1);
            } else if (c1 < c2) {
                b.add(nums[i]);
                t2.update(idx, 1);
            } else {
                if (a.size() > b.size()) {
                    b.add(nums[i]);
                    t2.update(idx, 1);
                } else {
                    a.add(nums[i]);
                    t1.update(idx, 1);
                }
            }
        }

        int[] result = new int[n];
        int index = 0;
        for (int x : a) result[index++] = x;
        for (int x : b) result[index++] = x;
        return result;
    }

    private int upperBound(List<int[]> list, int[] key) {
        int l = 0, r = list.size();
        while (l < r) {
            int m = (l + r) / 2;
            if (list.get(m)[0] > key[0] || (list.get(m)[0] == key[0] && list.get(m)[1] > key[1])) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
