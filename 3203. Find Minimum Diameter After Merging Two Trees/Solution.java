class Solution {
    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        List<List<Integer>> edgeList1 = convertToList(edges1);
        List<List<Integer>> edgeList2 = convertToList(edges2);

        int n1 = edgeList1.size() + 1;
        int n2 = edgeList2.size() + 1;

        List<Integer> depth1 = maxDepth(edgeList1);
        List<Integer> depth2 = maxDepth(edgeList2);

        int maxDepth1 = Collections.max(depth1);
        int maxDepth2 = Collections.max(depth2);

        int minInd1 = 0;
        for (int i = 1; i < n1; i++) {
            if (depth1.get(i) < depth1.get(minInd1)) minInd1 = i;
        }

        int minInd2 = 0;
        for (int i = 1; i < n2; i++) {
            if (depth2.get(i) < depth2.get(minInd2)) minInd2 = i;
        }

        int merged = Math.max(maxDepth1, Math.max(maxDepth2, depth1.get(minInd1) + depth2.get(minInd2)));
        return merged - 1;
    }

    private List<List<Integer>> convertToList(int[][] edges) {
        List<List<Integer>> list = new ArrayList<>();
        for (int[] edge : edges) {
            List<Integer> e = new ArrayList<>();
            e.add(edge[0]);
            e.add(edge[1]);
            list.add(e);
        }
        return list;
    }

    // Graph and DP arrays
    List<List<Integer>> g;
    int[] in, out;

    private void inCalculation(int src, int par) {
        in[src] = 1;

        for (int neighbor : g.get(src)) {
            if (neighbor == par) continue;
            inCalculation(neighbor, src);
            in[src] = Math.max(in[src], 1 + in[neighbor]);
        }
    }

    private void outCalculation(int src, int par) {
        List<Integer> down = new ArrayList<>();
        for (int neighbor : g.get(src)) {
            if (neighbor != par) {
                down.add(in[neighbor]);
            }
        }

        // Ensure at least two values to avoid IndexOutOfBounds
        down.add(0);
        down.add(0);
        Collections.sort(down);
        int mx1 = down.get(down.size() - 1);
        int mx2 = down.get(down.size() - 2);

        for (int neighbor : g.get(src)) {
            if (neighbor == par) continue;
            int maxIn = (in[neighbor] == mx1) ? mx2 : mx1;
            out[neighbor] = Math.max(1 + out[src], 1 + maxIn);
            outCalculation(neighbor, src);
        }
    }

    private List<Integer> maxDepth(List<List<Integer>> edges) {
        int n = edges.size() + 1;
        g = new ArrayList<>();
        in = new int[n];
        out = new int[n];

        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        for (List<Integer> edge : edges) {
            int u = edge.get(0), v = edge.get(1);
            g.get(u).add(v);
            g.get(v).add(u);
        }

        inCalculation(0, -1);
        outCalculation(0, -1);

        List<Integer> depth = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            depth.add(Math.max(in[i], 1 + out[i]));
        }
        return depth;
    }
}
