## LearningHub: A Pluggable Collaborative Machine Learning Framework for Android Applications
####  LearningHub, a novel pluggable personalized collaborative machine learning based Android application, as a study tool that uses Google's ML Firebase and ML Kit to improve the student's experience. By incorporating Google's ML Smart Response and Text Recognition models and collaboratively aggregating greater learning, Android apps can interact with the proposed collaborative learning framework (CLF) and convert text from images and data stored in emails. We employ an enhanced version of personalized federated learning. In addition to technical considerations, we examines the problem solving achieved to combine learning capabilities, advanced coding techniques, and user-centered mobile design principles. We investigate how LearningHub operates Google’s ML processing and Room’s SQL Lite local database to store sensitive user information and converted text and interacts/contributes continuously with the CLF framework. Our Android app design delves into how LearningHub uses Android's Private Compute Core (PCC) principles, with users controlling data (for example, in-app permissions). Our objective of projecting and implementing the CLF environment-based LearningHub under Android 12 with image handling using modern libraries such as Glide and Picasso is demonstrated in our implementation for personalized responding experiences. 

This repository accompanies 2023 paper.
### PROPOSED ARCHITECTURE: COLLABORATIVE LEARNING FRAMEWORK
![diagram](https://github.com/leakydishes/LearningHubApplication/blob/f789f42b301d98eba4b7503bff93635218d020e5/CLF.png)
###### Figure: A Pragmatic View on CLF Working Mechanism, 2023


### Overview
![diagram](https://github.com/leakydishes/LearningHubApplication/assets/79079577/a37944b5-e449-4b85-813e-1d772bb29e2c)
###### Figure 1. Application Overview, 2023


The Learning Hub application is a study tool which utilises Google’s ML Firebase and ML Kit to enhance the educational experience for students. By incorporating Google’s ML Smart Reply and ML Text Recognition models, users can interact with an AI system, convert text from images and email stored data. Application operates Google’s ML processing and Room’s SQL Lite local database for storing sensitive user information and converted text. Supporting Android’s Private Compute Core (PCC) principles, with users controlling data (inapplication permissions). 


### Application ScreenShots
![overviewScreenshots](https://github.com/leakydishes/LearningHubApplication/assets/79079577/a18b24a7-26f7-43a9-8541-a2b5ad9f9032)
###### Figure 2. Application ScreenShots, 2023


### Overview ML Kit
![diagram2](https://github.com/leakydishes/LearningHubApplication/assets/79079577/6c56f4b5-97ee-47fa-b035-64770b9e1f0e)
###### Figure 3. ML Kit 1, 2023


![diagram3](https://github.com/leakydishes/LearningHubApplication/assets/79079577/86af24b4-84d4-45e8-8b6b-9380cc018943)
###### Figure 4. ML Kit 2, 2023

The personalized federated learning implementation is build on top of https://github.com/JianXu95/FedPAC.

### References
[1] B. McMahan et al., “Communication-efficient learning of deep net-
works from decentralized data,” in Artificial intelligence and statistics,
pp. 1273–1282, PMLR, 2017.
[2] S. R. Pokhrel and J. Choi, “Federated learning with blockchain for au-
tonomous vehicles: Analysis and design challenges,” IEEE Transactions
on Communications, vol. 68, no. 8, pp. 4734–4746, 2020.
[3] J. Grundy, “Human-centric software engineering for next genera-
tion cloud- and edge-based smart living applications,” in 2020 20th
IEEE/ACM International Symposium on Cluster, Cloud and Internet
Computing (CCGRID), pp. 1–10, 2020.
[4] A. Z. Tan, H. Yu, L. Cui, and Q. Yang, “Towards personalized federated
learning,” IEEE Transactions on Neural Networks and Learning Systems,
2022.
[5] J. Xu, X. Tong, and S.-L. Huang, “Personalized federated learning with
fea- ture alignment and classifier collaboration,”
[6] E. Marchiori et al., “Android private compute core architecture,” arXiv
preprint arXiv:2209.10317, 2022.
[7] K. Gopal et al., “Text and facial features recognition using machine
intelligence,” in 2022 International Conference on Machine Learning,
Big Data, Cloud and Parallel Computing (COM-IT-CON), vol. 1,
pp. 333–337, IEEE, 2022.
[8] A. Kumar, Mastering Firebase for Android Development: Build real-
time, scalable, and cloud-enabled Android apps with Firebase. Birm-
ingham, UK: Packt Publishing, 2018.
[9] N. Karthikeyan, Machine Learning Projects for Mobile Applications:
Build Android and iOS applications using TensorFlow Lite and Core
ML. Birmingham, United Kingdom: Packt Publishing, 2018.
[10] H. Khalajzadeh, M. Shahin, H. O. Obie, P. Agrawal, and J. Grundy,
“Supporting developers in addressing human-centric issues in mobile
apps,” IEEE Transactions on Software Engineering, vol. 49, no. 4,
pp. 2149–2168, 2023.
[11] A. Kannan, K. Kurach, S. Ravi, T. Kaufmann, A. Tomkins, B. Miklos,
G. Corrado, L. Lukacs, M. Ganea, P. Young, and V. Ramavajjala, “Smart
Reply: Automated Response Suggestion for Email,” in Proceedings
of the 22nd ACM SIGKDD International Conference on Knowledge
Discovery and Data Mining, pp. 955–964, ACM.
[12] A. Kannan et al., “Smart Reply: Automated Response Suggestion
for Email,” in Proceedings of the 22nd ACM SIGKDD International
Conference on Knowledge Discovery and Data Mining, pp. 955–964,
ACM.
[13] Google, “ML Kit for Firebase,” 2023.

