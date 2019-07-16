.class public Lcom/deanxd/hotfix/MainActivity_CF;
.super Landroid/support/v7/app/m;
.source ""


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Landroid/support/v7/app/m;-><init>()V

    return-void
.end method


# virtual methods
.method public loadPatch(Landroid/view/View;)V
    .locals 1

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v0, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, "andFix.apatch"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {}, Lb/a/a/a/a;->a()Lb/a/a/a/a;

    move-result-object v0

    invoke-virtual {v0, p1}, Lb/a/a/a/a;->a(Ljava/lang/String;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 0

    invoke-super {p0, p1}, Landroid/support/v7/app/m;->onCreate(Landroid/os/Bundle;)V

    const p1, 0x7f09001c

    invoke-virtual {p0, p1}, Landroid/support/v7/app/m;->setContentView(I)V

    invoke-static {}, Lb/a/a/a/a;->a()Lb/a/a/a/a;

    move-result-object p1

    invoke-virtual {p1, p0}, Lb/a/a/a/a;->a(Landroid/content/Context;)V

    return-void
.end method

.method public test(Landroid/view/View;)V
    .locals 1
    .annotation runtime Lcom/alipay/euler/andfix/annotation/MethodReplace;
        clazz = "com.deanxd.hotfix.MainActivity"
        method = "test"
    .end annotation

    const-string p1, "this is new apk"

    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    return-void
.end method
