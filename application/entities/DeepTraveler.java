package application.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeepTraveler {

    private Tag tagRoot;

    public DeepTraveler() {
        this.tagRoot = null;
    }

    public Tag getTagRoot() {
        return tagRoot;
    }

    public void setUrl(URL url) throws IOException {
        String html = fetchHtmlFromUrl(url);
        readHtml(html);
    }

    private String fetchHtmlFromUrl(URL url) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        }
        return htmlContent.toString();
    }

    public void readHtml(String text) {
        String regex = "<(\\w+)[^>]*>(.*?)</\\1>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            tagRoot = parseTag(matcher.group(1), matcher.group(2), 0);
        } else {
            System.out.println("Nenhuma tag encontrada.");
        }
    }

    private Tag parseTag(String tagName, String content, int level) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tag.setLevel(level);

        String regex = "<(\\w+)[^>]*>(.*?)</\\1>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);

        boolean hasChildren = false;

        while (matcher.find()) {
            hasChildren = true;
            tag.addTag(parseTag(matcher.group(1), matcher.group(2), level + 1));
        }

        if (!hasChildren) {
            tag.setValue(content.trim());
        }

        return tag;
    }

    public String deepestText() {
        if (tagRoot == null) {
            return null;
        }
        return findDeepestText(tagRoot, 0).deepest;
    }

    private static class DepthInfo {
        int maxDepth;
        String deepest;

        DepthInfo(int maxDepth, String deepest) {
            this.maxDepth = maxDepth;
            this.deepest = deepest;
        }
    }

    private DepthInfo findDeepestText(Tag tag, int depth) {
        if (tag == null) return new DepthInfo(-1, null);

        DepthInfo maxInfo = new DepthInfo(depth, tag.getValue());

        for (Tag child : tag.getChildrenTag()) {
            DepthInfo childInfo = findDeepestText(child, depth + 1);
            if (childInfo.maxDepth > maxInfo.maxDepth) {
                maxInfo = childInfo;
            }
        }
        return maxInfo;
    }
}
